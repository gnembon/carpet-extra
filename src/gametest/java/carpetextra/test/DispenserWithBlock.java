package carpetextra.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import carpetextra.machinery.DynamicTest;
import carpetextra.machinery.TestProvider;
import carpetextra.mixins.AxeItem_StrippedBlocksAccessorMixin;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameMode;

public class DispenserWithBlock {
    static final String STRUCTURE = "carpet-extra:dispenserbase";
    static final String GEN_PREFIX = "dispenser_with_block";
    static final String ENV = "carpet-extra:dispenserwithblock"; // turns rules on
    static final int DISPENSER_DELAY = 4;
    BlockPos lapis = new BlockPos(2, 0, 0);
    BlockPos button = new BlockPos(0, 1, 0);
    BlockPos dispenser = new BlockPos(1, 1, 0);
    
    @GameTest(structure = STRUCTURE, environment = ENV)
    public void renewableNetherrack(TestContext ctx) {
        blockConversionTest(ctx, Items.FIRE_CHARGE, Blocks.COBBLESTONE, Blocks.NETHERRACK, 1, true);
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
    public void renewableEndstone(TestContext ctx) {
        blockConversionTest(ctx, Items.DRAGON_BREATH, Blocks.COBBLESTONE, Blocks.END_STONE, 1, true);
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
    public void blazeMeal(TestContext ctx) {
        putInDispenser(ctx, Items.BLAZE_POWDER.getDefaultStack());
        ctx.setBlockState(lapis, Blocks.SOUL_SAND);
        ctx.setBlockState(lapis.up(), Blocks.NETHER_WART);
        
        ctx.pushButton(button);
        
        ctx.addFinalTaskWithDuration(DISPENSER_DELAY, () -> {
            ctx.expectEmptyContainer(dispenser);
            ctx.expectBlockProperty(lapis.up(), NetherWartBlock.AGE, 1);
        });
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
    public void blazeMealMaxed(TestContext ctx) {
        putInDispenser(ctx, Items.BLAZE_POWDER.getDefaultStack());
        ctx.setBlockState(lapis, Blocks.SOUL_SAND);
        ctx.setBlockState(lapis.up(), Blocks.NETHER_WART.getDefaultState().with(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE));
        
        ctx.pushButton(button);
        
        ctx.runAtTick(DISPENSER_DELAY, () -> {
            checkFirstSlotHas(ctx, Items.BLAZE_POWDER, false);
            ctx.expectBlockProperty(lapis.up(), NetherWartBlock.AGE, NetherWartBlock.MAX_AGE);
            ctx.complete();
        });
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
    public void shearPumpkin(TestContext ctx) {
        blockConversionTest(ctx, Items.SHEARS, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, 1, false, () -> ctx.expectItem(Items.PUMPKIN_SEEDS));
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
    public void shearPumpkinBreaks(TestContext ctx) {
        blockConversionTest(ctx, Items.SHEARS, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, 1, true, () -> ctx.expectItem(Items.PUMPKIN_SEEDS));
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
    public void boatOnRegularIce(TestContext ctx) {
        boatTest(ctx, Items.OAK_BOAT, Blocks.ICE, EntityType.OAK_BOAT);
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
    public void boatOnPackedIce(TestContext ctx) {
        boatTest(ctx, Items.OAK_BOAT, Blocks.PACKED_ICE, EntityType.OAK_BOAT);
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
    public void boatOnBlueIce(TestContext ctx) {
        boatTest(ctx, Items.OAK_BOAT, Blocks.BLUE_ICE, EntityType.OAK_BOAT);
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
    public void chestBoatOnIce(TestContext ctx) {
        boatTest(ctx, Items.OAK_CHEST_BOAT, Blocks.ICE, EntityType.OAK_CHEST_BOAT);
    }
    
    private void boatTest(TestContext ctx, Item item, Block block, EntityType<?> expectedEntity) {
        putInDispenser(ctx, item.getDefaultStack());
        ctx.setBlockState(lapis, block);
        
        ctx.pushButton(button);
        ctx.runAtTick(DISPENSER_DELAY, () -> {
            ctx.expectEmptyContainer(dispenser);
            ctx.expectEntityAt(expectedEntity, lapis.up());
            
            Entity boat = ctx.getEntities(expectedEntity).getFirst();
            
            PlayerEntity p = ctx.createMockPlayer(GameMode.SURVIVAL);
            for (int i = 0; i < 20; i++) p.attack(boat); // just kill doesn't drop it
            
            ctx.expectItem(item);
            ctx.complete();
        });
    }

    @TestProvider
    public Collection<DynamicTest> stripTests() {
        List<DynamicTest> fns = new ArrayList<>();
        Map<Block, Block> conversions = AxeItem_StrippedBlocksAccessorMixin.getStrippedBlocks();
        
        for (Map.Entry<Block, Block> entry : conversions.entrySet()) {
            fns.add(makeDispenserTest("strip_" + entry.getKey().asItem(), (ctx) -> {
                stripTest(ctx, Items.IRON_AXE, entry.getKey(), entry.getValue());
            }));
        }
        for (Item tool : List.of(Items.WOODEN_AXE, Items.STONE_AXE, Items.GOLDEN_AXE, Items.IRON_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE)) {
            fns.add(makeDispenserTest("stripwith" + tool, (ctx) -> {
                stripTest(ctx, tool, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
            }));
        }
        // Copper. There's no way I'm adding all the coppers
        fns.add(makeDispenserTest("stripDeoxidateCopper", (ctx) -> {
            stripTest(ctx, Items.IRON_AXE, Blocks.WEATHERED_COPPER, Blocks.EXPOSED_COPPER);
        }));
        fns.add(makeDispenserTest("stripUnwaxCopper", (ctx) -> {
            stripTest(ctx, Items.IRON_AXE, Blocks.WAXED_COPPER_BLOCK, Blocks.COPPER_BLOCK);
        }));
        
        return fns;
    }
    
    private void stripTest(TestContext ctx, Item tool, Block blockFrom, Block blockTo) {
        blockConversionTest(ctx, tool, blockFrom, blockTo, 1, false);
    }
    
    @TestProvider
    public Collection<DynamicTest> cauldronTests() {
        List<DynamicTest> fns = new ArrayList<>();
        for (int i = 0; i < 2; i++) { // 3 is handled separately
            for (int off = -1; off <= 1; off += 2) {
                int finalOff = off;
                int adjustedI = off > 0 ? i : i + 1;
                fns.add(makeDispenserTest("cauldronBottleLvl" + adjustedI + (off > 0 ? "Fill" : "Empty"), ctx -> {
                    putInDispenser(ctx, finalOff > 0 ? waterBottle() : Items.GLASS_BOTTLE.getDefaultStack());
                    ctx.setBlockState(lapis.up(), waterCauldronFor(ctx, adjustedI));
                    BlockPos referencePos = dispenser.down();
                    ctx.setBlockState(referencePos, waterCauldronFor(ctx, adjustedI + finalOff));
                    
                    ctx.pushButton(button);
                    
                    ctx.addFinalTaskWithDuration(DISPENSER_DELAY, () -> {
                        ctx.expectSameStates(lapis.up(), referencePos);
                        checkFirstSlotHas(ctx, finalOff > 0 ? Items.GLASS_BOTTLE : Items.POTION, false);
                    });
                }));
            }
        }
        // TODO powdered snow
        for (Map.Entry<Item, Block> entry : Map.of(Items.LAVA_BUCKET, Blocks.LAVA_CAULDRON
                                //Items.POWDER_SNOW_BUCKET, Blocks.POWDER_SNOW_CAULDRON
                                ).entrySet()) {
            fns.add(makeDispenserTest("cauldronFillWith" + entry.getKey(), (ctx) -> {
                blockConversionTest(ctx, entry.getKey(), Blocks.CAULDRON, entry.getValue(), 1, false, () -> {
                    checkFirstSlotHas(ctx, Items.BUCKET, false);
                });
            }));
            fns.add(makeDispenserTest("cauldronEmptyTo" + entry.getKey(), (ctx) -> {
                blockConversionTest(ctx, Items.BUCKET, entry.getValue(), Blocks.CAULDRON, 1, false, () -> {
                    checkFirstSlotHas(ctx, entry.getKey(), false);
                });
            }));
        }
        return fns;
    }
    
    private static ItemStack waterBottle() {
        return PotionContentsComponent.createStack(Items.POTION, Potions.WATER);
    }
    
    private BlockState waterCauldronFor(TestContext ctx, int level) {
        if (level == 0) {
            return Blocks.CAULDRON.getDefaultState();
        }
        return Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, level);
    }

    @TestProvider
    public Collection<DynamicTest> tillTests() {
        List<DynamicTest> fns = new ArrayList<>();
        fns.add(makeDispenserTest("tillDirtAbove", (ctx) -> {
            blockConversionTest(ctx, Items.IRON_HOE, Blocks.DIRT, Blocks.FARMLAND, 1, false);
        }));
        fns.add(makeDispenserTest("tillGrass", (ctx) -> {
            blockConversionTest(ctx, Items.IRON_HOE, Blocks.GRASS_BLOCK, Blocks.FARMLAND, 0, false);
        }));
        fns.add(makeDispenserTest("tillCheckDurability", (ctx) -> {
            blockConversionTest(ctx, Items.IRON_HOE, Blocks.DIRT, Blocks.FARMLAND, 0, true);
        }));
        fns.add(makeDispenserTest("tillCoarseDirt", (ctx) -> {
            blockConversionTest(ctx, Items.IRON_HOE, Blocks.COARSE_DIRT, Blocks.DIRT, 0, false);
        }));
        fns.add(makeDispenserTest("tillRootedDirt", (ctx) -> {
            blockConversionTest(ctx, Items.IRON_HOE, Blocks.ROOTED_DIRT, Blocks.DIRT, 0, false, () -> ctx.expectItem(Items.HANGING_ROOTS));
        }));
        
        for (Item hoe : List.of(Items.WOODEN_HOE, Items.STONE_HOE, Items.GOLDEN_HOE, Items.IRON_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE)) {
            fns.add(makeDispenserTest("tillDirtWith" + hoe, (ctx) -> {
                blockConversionTest(ctx, hoe, Blocks.DIRT, Blocks.FARMLAND, 0, false);
            }));
        }

        return fns;
    }

    private void blockConversionTest(TestContext ctx, Item tool, Block from, Block to, int offset, boolean putDamaged, Runnable... extras) {
        if (putDamaged) {
            putAtOneDurability(ctx, tool);
        } else {
            putInDispenser(ctx, tool.getDefaultStack());
        }
        ctx.setBlockState(lapis.offset(Direction.UP, offset), from);
        ctx.pushButton(button);
        
        ctx.addFinalTaskWithDuration(DISPENSER_DELAY, () -> {
            ctx.expectBlock(to, lapis.offset(Direction.UP, offset));
            if (putDamaged) {
                ctx.expectEmptyContainer(dispenser);
            } else if (tool.getDefaultStack().isDamageable()) { // otherwise we expect extras to handle it
                checkFirstSlotHas(ctx, tool, true);
            }
            runAll(extras);
        });
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
    public void fillMinecartChest(TestContext ctx) {
        cartTest(ctx, Items.CHEST, EntityType.CHEST_MINECART);
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
    public void fillMinecartHopper(TestContext ctx) {
        cartTest(ctx, Items.HOPPER, EntityType.HOPPER_MINECART);
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
    public void fillMinecartTnt(TestContext ctx) {
        cartTest(ctx, Items.TNT, EntityType.TNT_MINECART, () -> ctx.dontExpectEntity(EntityType.TNT));
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
    public void fillMinecartFurnace(TestContext ctx) {
        cartTest(ctx, Items.FURNACE, EntityType.FURNACE_MINECART);
    }
    
    private void cartTest(TestContext ctx, Item item, EntityType<?> entity, Runnable... extras) {
        putInDispenser(ctx, item.getDefaultStack());
        ctx.setBlockState(lapis.up(), Blocks.RAIL);
        ctx.spawnEntity(EntityType.MINECART, lapis.up());
        
        ctx.pushButton(button);
        ctx.addFinalTaskWithDuration(DISPENSER_DELAY, () -> {
            ctx.expectEntityAt(entity, lapis.up());
            ctx.dontExpectEntity(EntityType.MINECART);
            ctx.dontExpectEntity(EntityType.ITEM);
            runAll(extras);
        });
    }
    
    // very basic autocrafting test, for now just to catch simple crashes or malfunctioning stuff
    @GameTest(structure = STRUCTURE, environment = ENV)
    public void craftCake(TestContext ctx) {
        Item[] recipe = new Item[] {
                Items.MILK_BUCKET, Items.MILK_BUCKET, Items.MILK_BUCKET,
                Items.SUGAR,       Items.EGG,         Items.SUGAR,
                Items.WHEAT,       Items.WHEAT,       Items.WHEAT
        };
        ctx.setBlockState(dispenser, Blocks.DROPPER.getStateWithProperties(ctx.getBlockState(dispenser)));
        ctx.setBlockState(lapis.up(), Blocks.CRAFTING_TABLE);
        for (int i = 0; i < 9; i++) {
            ctx.getBlockEntity(dispenser, DispenserBlockEntity.class).setStack(i, recipe[i].getDefaultStack());
        }
        ctx.pushButton(button);
        
        ctx.addFinalTaskWithDuration(DISPENSER_DELAY, () -> {
            ctx.expectItem(Items.CAKE);
            for (Item item : recipe) ctx.dontExpectItem(item);
            for (int i = 0; i < 3; i++) {
                int finalI = i;
                
                ctx.checkBlockEntity(dispenser, DispenserBlockEntity.class,
                        disp -> disp.getStack(finalI).getItem() == Items.BUCKET, 
                        msg("Must have buckets remaining in dispenser"));
            }
            for (int i = 3; i < 9; i++) {
                int finalI = i;
                ctx.checkBlockEntity(dispenser, DispenserBlockEntity.class,
                        disp -> disp.getStack(finalI).isEmpty(), 
                        msg("Must not have anything but the first 3 buckets in dispenser"));
            }
        });
    }
    
    // Util
    private void putInDispenser(TestContext ctx, ItemStack item) {
        ctx.getBlockEntity(dispenser, DispenserBlockEntity.class).addToFirstFreeSlot(item);
    }
    
    private void putAtOneDurability(TestContext ctx, Item item) {
        ItemStack stack = item.getDefaultStack();
        stack.setDamage(stack.getMaxDamage() - 1);
        putInDispenser(ctx, stack);
    }
    
    private void checkFirstSlotHas(TestContext ctx, Item item, boolean damaged) {
        ctx.checkBlockEntity(dispenser, DispenserBlockEntity.class,
                disp -> disp.getStack(0).getItem() == item && (!damaged || disp.getStack(0).isDamaged()), 
                msg("Must have " + (damaged ? "damaged " : "") + item + " in dispenser"));
    }
    
    private void runAll(Runnable... actions) {
        for (Runnable r : actions) r.run();
    }
    private Supplier<Text> msg(String str) {
        return () -> Text.literal(str);
    }
    
    // Setup util
    private DynamicTest makeDispenserTest(String name, Consumer<TestContext> runner) {
        name = GEN_PREFIX + '.' + name.replace("minecraft:", "");
        return new DynamicTest(ENV, name, STRUCTURE, 20, 0, true, runner);
    }
}
