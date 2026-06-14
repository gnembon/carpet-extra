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
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

public class DispenserWithBlock {
    static final String STRUCTURE = "carpet-extra:dispenserbase";
    static final String GEN_PREFIX = "dispenser_with_block";
    static final String ENV = "carpet-extra:dispenserwithblock"; // turns rules on
    static final int DISPENSER_DELAY = 4;
    BlockPos lapis = new BlockPos(2, 0, 0);
    BlockPos button = new BlockPos(0, 1, 0);
    BlockPos dispenser = new BlockPos(1, 1, 0);
    
    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void renewableNetherrack(GameTestHelper ctx) {
=======
    public void renewableNetherrack(TestContext ctx) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        blockConversionTest(ctx, Items.FIRE_CHARGE, Blocks.COBBLESTONE, Blocks.NETHERRACK, 1, true);
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void renewableEndstone(GameTestHelper ctx) {
=======
    public void renewableEndstone(TestContext ctx) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        blockConversionTest(ctx, Items.DRAGON_BREATH, Blocks.COBBLESTONE, Blocks.END_STONE, 1, true);
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void blazeMeal(GameTestHelper ctx) {
        putInDispenser(ctx, Items.BLAZE_POWDER.getDefaultInstance());
        ctx.setBlock(lapis, Blocks.SOUL_SAND);
        ctx.setBlock(lapis.above(), Blocks.NETHER_WART);
        
        ctx.pressButton(button);
        
        ctx.succeedOnTickWhen(DISPENSER_DELAY, () -> {
            ctx.assertContainerEmpty(dispenser);
            ctx.assertBlockProperty(lapis.above(), NetherWartBlock.AGE, 1);
=======
    public void blazeMeal(TestContext ctx) {
        putInDispenser(ctx, Items.BLAZE_POWDER.getDefaultStack());
        ctx.setBlockState(lapis, Blocks.SOUL_SAND);
        ctx.setBlockState(lapis.up(), Blocks.NETHER_WART);
        
        ctx.pushButton(button);
        
        ctx.addFinalTaskWithDuration(DISPENSER_DELAY, () -> {
            ctx.expectEmptyContainer(dispenser);
            ctx.expectBlockProperty(lapis.up(), NetherWartBlock.AGE, 1);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        });
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void blazeMealMaxed(GameTestHelper ctx) {
        putInDispenser(ctx, Items.BLAZE_POWDER.getDefaultInstance());
        ctx.setBlock(lapis, Blocks.SOUL_SAND);
        ctx.setBlock(lapis.above(), Blocks.NETHER_WART.defaultBlockState().setValue(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE));
        
        ctx.pressButton(button);
        
        ctx.runAtTickTime(DISPENSER_DELAY, () -> {
            checkFirstSlotHas(ctx, Items.BLAZE_POWDER, false);
            ctx.assertBlockProperty(lapis.above(), NetherWartBlock.AGE, NetherWartBlock.MAX_AGE);
            ctx.succeed();
=======
    public void blazeMealMaxed(TestContext ctx) {
        putInDispenser(ctx, Items.BLAZE_POWDER.getDefaultStack());
        ctx.setBlockState(lapis, Blocks.SOUL_SAND);
        ctx.setBlockState(lapis.up(), Blocks.NETHER_WART.getDefaultState().with(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE));
        
        ctx.pushButton(button);
        
        ctx.runAtTick(DISPENSER_DELAY, () -> {
            checkFirstSlotHas(ctx, Items.BLAZE_POWDER, false);
            ctx.expectBlockProperty(lapis.up(), NetherWartBlock.AGE, NetherWartBlock.MAX_AGE);
            ctx.complete();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        });
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void shearPumpkin(GameTestHelper ctx) {
        blockConversionTest(ctx, Items.SHEARS, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, 1, false, () -> ctx.assertItemEntityPresent(Items.PUMPKIN_SEEDS));
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
    public void shearPumpkinBreaks(GameTestHelper ctx) {
        blockConversionTest(ctx, Items.SHEARS, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, 1, true, () -> ctx.assertItemEntityPresent(Items.PUMPKIN_SEEDS));
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
    public void boatOnRegularIce(GameTestHelper ctx) {
=======
    public void shearPumpkin(TestContext ctx) {
        blockConversionTest(ctx, Items.SHEARS, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, 1, false, () -> ctx.expectItem(Items.PUMPKIN_SEEDS));
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
    public void shearPumpkinBreaks(TestContext ctx) {
        blockConversionTest(ctx, Items.SHEARS, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, 1, true, () -> ctx.expectItem(Items.PUMPKIN_SEEDS));
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
    public void boatOnRegularIce(TestContext ctx) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        boatTest(ctx, Items.OAK_BOAT, Blocks.ICE, EntityType.OAK_BOAT);
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void boatOnPackedIce(GameTestHelper ctx) {
=======
    public void boatOnPackedIce(TestContext ctx) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        boatTest(ctx, Items.OAK_BOAT, Blocks.PACKED_ICE, EntityType.OAK_BOAT);
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void boatOnBlueIce(GameTestHelper ctx) {
=======
    public void boatOnBlueIce(TestContext ctx) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        boatTest(ctx, Items.OAK_BOAT, Blocks.BLUE_ICE, EntityType.OAK_BOAT);
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void chestBoatOnIce(GameTestHelper ctx) {
        boatTest(ctx, Items.OAK_CHEST_BOAT, Blocks.ICE, EntityType.OAK_CHEST_BOAT);
    }
    
    private void boatTest(GameTestHelper ctx, Item item, Block block, EntityType<?> expectedEntity) {
        putInDispenser(ctx, item.getDefaultInstance());
        ctx.setBlock(lapis, block);
        
        ctx.pressButton(button);
        ctx.runAtTickTime(DISPENSER_DELAY, () -> {
            ctx.assertContainerEmpty(dispenser);
            ctx.assertEntityPresent(expectedEntity, lapis.above());
            
            Entity boat = ctx.getEntities(expectedEntity).getFirst();
            
            Player p = ctx.makeMockPlayer(GameType.SURVIVAL);
            for (int i = 0; i < 20; i++) p.attack(boat); // just kill doesn't drop it
            
            ctx.assertItemEntityPresent(item);
            ctx.succeed();
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
    
<<<<<<< HEAD
    private void stripTest(GameTestHelper ctx, Item tool, Block blockFrom, Block blockTo) {
=======
    private void stripTest(TestContext ctx, Item tool, Block blockFrom, Block blockTo) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
<<<<<<< HEAD
                    putInDispenser(ctx, finalOff > 0 ? waterBottle() : Items.GLASS_BOTTLE.getDefaultInstance());
                    ctx.setBlock(lapis.above(), waterCauldronFor(ctx, adjustedI));
                    BlockPos referencePos = dispenser.below();
                    ctx.setBlock(referencePos, waterCauldronFor(ctx, adjustedI + finalOff));
                    
                    ctx.pressButton(button);
                    
                    ctx.succeedOnTickWhen(DISPENSER_DELAY, () -> {
                        ctx.assertSameBlockState(lapis.above(), referencePos);
=======
                    putInDispenser(ctx, finalOff > 0 ? waterBottle() : Items.GLASS_BOTTLE.getDefaultStack());
                    ctx.setBlockState(lapis.up(), waterCauldronFor(ctx, adjustedI));
                    BlockPos referencePos = dispenser.down();
                    ctx.setBlockState(referencePos, waterCauldronFor(ctx, adjustedI + finalOff));
                    
                    ctx.pushButton(button);
                    
                    ctx.addFinalTaskWithDuration(DISPENSER_DELAY, () -> {
                        ctx.expectSameStates(lapis.up(), referencePos);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
<<<<<<< HEAD
        return PotionContents.createItemStack(Items.POTION, Potions.WATER);
    }
    
    private BlockState waterCauldronFor(GameTestHelper ctx, int level) {
        if (level == 0) {
            return Blocks.CAULDRON.defaultBlockState();
        }
        return Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, level);
=======
        return PotionContentsComponent.createStack(Items.POTION, Potions.WATER);
    }
    
    private BlockState waterCauldronFor(TestContext ctx, int level) {
        if (level == 0) {
            return Blocks.CAULDRON.getDefaultState();
        }
        return Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, level);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
<<<<<<< HEAD
            blockConversionTest(ctx, Items.IRON_HOE, Blocks.ROOTED_DIRT, Blocks.DIRT, 0, false, () -> ctx.assertItemEntityPresent(Items.HANGING_ROOTS));
=======
            blockConversionTest(ctx, Items.IRON_HOE, Blocks.ROOTED_DIRT, Blocks.DIRT, 0, false, () -> ctx.expectItem(Items.HANGING_ROOTS));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }));
        
        for (Item hoe : List.of(Items.WOODEN_HOE, Items.STONE_HOE, Items.GOLDEN_HOE, Items.IRON_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE)) {
            fns.add(makeDispenserTest("tillDirtWith" + hoe, (ctx) -> {
                blockConversionTest(ctx, hoe, Blocks.DIRT, Blocks.FARMLAND, 0, false);
            }));
        }

        return fns;
    }

<<<<<<< HEAD
    private void blockConversionTest(GameTestHelper ctx, Item tool, Block from, Block to, int offset, boolean putDamaged, Runnable... extras) {
        if (putDamaged) {
            putAtOneDurability(ctx, tool);
        } else {
            putInDispenser(ctx, tool.getDefaultInstance());
        }
        ctx.setBlock(lapis.relative(Direction.UP, offset), from);
        ctx.pressButton(button);
        
        ctx.succeedOnTickWhen(DISPENSER_DELAY, () -> {
            ctx.assertBlockPresent(to, lapis.relative(Direction.UP, offset));
            if (putDamaged) {
                ctx.assertContainerEmpty(dispenser);
            } else if (tool.getDefaultInstance().isDamageableItem()) { // otherwise we expect extras to handle it
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                checkFirstSlotHas(ctx, tool, true);
            }
            runAll(extras);
        });
    }
    
    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void fillMinecartChest(GameTestHelper ctx) {
=======
    public void fillMinecartChest(TestContext ctx) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        cartTest(ctx, Items.CHEST, EntityType.CHEST_MINECART);
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void fillMinecartHopper(GameTestHelper ctx) {
=======
    public void fillMinecartHopper(TestContext ctx) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        cartTest(ctx, Items.HOPPER, EntityType.HOPPER_MINECART);
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void fillMinecartTnt(GameTestHelper ctx) {
        cartTest(ctx, Items.TNT, EntityType.TNT_MINECART, () -> ctx.assertEntityNotPresent(EntityType.TNT));
    }

    @GameTest(structure = STRUCTURE, environment = ENV)
    public void fillMinecartFurnace(GameTestHelper ctx) {
        cartTest(ctx, Items.FURNACE, EntityType.FURNACE_MINECART);
    }
    
    private void cartTest(GameTestHelper ctx, Item item, EntityType<?> entity, Runnable... extras) {
        putInDispenser(ctx, item.getDefaultInstance());
        ctx.setBlock(lapis.above(), Blocks.RAIL);
        ctx.spawn(EntityType.MINECART, lapis.above());
        
        ctx.pressButton(button);
        ctx.succeedOnTickWhen(DISPENSER_DELAY, () -> {
            ctx.assertEntityPresent(entity, lapis.above());
            ctx.assertEntityNotPresent(EntityType.MINECART);
            ctx.assertEntityNotPresent(EntityType.ITEM);
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            runAll(extras);
        });
    }
    
    // very basic autocrafting test, for now just to catch simple crashes or malfunctioning stuff
    @GameTest(structure = STRUCTURE, environment = ENV)
<<<<<<< HEAD
    public void craftCake(GameTestHelper ctx) {
=======
    public void craftCake(TestContext ctx) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        Item[] recipe = new Item[] {
                Items.MILK_BUCKET, Items.MILK_BUCKET, Items.MILK_BUCKET,
                Items.SUGAR,       Items.EGG,         Items.SUGAR,
                Items.WHEAT,       Items.WHEAT,       Items.WHEAT
        };
<<<<<<< HEAD
        ctx.setBlock(dispenser, Blocks.DROPPER.withPropertiesOf(ctx.getBlockState(dispenser)));
        ctx.setBlock(lapis.above(), Blocks.CRAFTING_TABLE);
        for (int i = 0; i < 9; i++) {
            ctx.getBlockEntity(dispenser, DispenserBlockEntity.class).setItem(i, recipe[i].getDefaultInstance());
        }
        ctx.pressButton(button);
        
        ctx.succeedOnTickWhen(DISPENSER_DELAY, () -> {
            ctx.assertItemEntityPresent(Items.CAKE);
            for (Item item : recipe) ctx.assertItemEntityNotPresent(item);
            for (int i = 0; i < 3; i++) {
                int finalI = i;
                
                ctx.assertBlockEntityData(dispenser, DispenserBlockEntity.class,
                        disp -> disp.getItem(finalI).getItem() == Items.BUCKET, 
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                        msg("Must have buckets remaining in dispenser"));
            }
            for (int i = 3; i < 9; i++) {
                int finalI = i;
<<<<<<< HEAD
                ctx.assertBlockEntityData(dispenser, DispenserBlockEntity.class,
                        disp -> disp.getItem(finalI).isEmpty(), 
=======
                ctx.checkBlockEntity(dispenser, DispenserBlockEntity.class,
                        disp -> disp.getStack(finalI).isEmpty(), 
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                        msg("Must not have anything but the first 3 buckets in dispenser"));
            }
        });
    }
    
    // Util
<<<<<<< HEAD
    private void putInDispenser(GameTestHelper ctx, ItemStack item) {
        ctx.getBlockEntity(dispenser, DispenserBlockEntity.class).insertItem(item);
    }
    
    private void putAtOneDurability(GameTestHelper ctx, Item item) {
        ItemStack stack = item.getDefaultInstance();
        stack.setDamageValue(stack.getMaxDamage() - 1);
        putInDispenser(ctx, stack);
    }
    
    private void checkFirstSlotHas(GameTestHelper ctx, Item item, boolean damaged) {
        ctx.assertBlockEntityData(dispenser, DispenserBlockEntity.class,
                disp -> disp.getItem(0).getItem() == item && (!damaged || disp.getItem(0).isDamaged()), 
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                msg("Must have " + (damaged ? "damaged " : "") + item + " in dispenser"));
    }
    
    private void runAll(Runnable... actions) {
        for (Runnable r : actions) r.run();
    }
<<<<<<< HEAD
    private Supplier<Component> msg(String str) {
        return () -> Component.literal(str);
    }
    
    // Setup util
    private DynamicTest makeDispenserTest(String name, Consumer<GameTestHelper> runner) {
=======
    private Supplier<Text> msg(String str) {
        return () -> Text.literal(str);
    }
    
    // Setup util
    private DynamicTest makeDispenserTest(String name, Consumer<TestContext> runner) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        name = GEN_PREFIX + '.' + name.replace("minecraft:", "");
        return new DynamicTest(ENV, name, STRUCTURE, 20, 0, true, runner);
    }
}
