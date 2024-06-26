package carpetextra.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import carpetextra.CarpetExtraSettings;
import carpetextra.mixins.AxeItem_StrippedBlocksAccessorMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.test.AfterBatch;
import net.minecraft.test.BeforeBatch;
import net.minecraft.test.CustomTestProvider;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.test.TestFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class DispenserWithBlock {
    static final String STRUCTURE = "carpet-extra:dispenserbase";
    static final String BATCH = "dispenserwithblock";
    static final int DISPENSER_DELAY = 4;
    BlockPos lapis = new BlockPos(2, 1, 0);
    BlockPos button = new BlockPos(0, 2, 0);
    BlockPos dispenser = new BlockPos(1, 2, 0);
    
    @BeforeBatch(batchId = BATCH)
    public void before(ServerWorld world) {
        CarpetExtraSettings.dispensersFillMinecarts = true;
        CarpetExtraSettings.dispensersCarvePumpkins = true;
        CarpetExtraSettings.dispensersStripBlocks = true;
        CarpetExtraSettings.dispensersTillSoil = true;
        CarpetExtraSettings.dispensersUseCauldrons = true;
    }
    
    @AfterBatch(batchId = BATCH)
    public void after(ServerWorld world) {
        CarpetExtraSettings.dispensersFillMinecarts = false;
        CarpetExtraSettings.dispensersCarvePumpkins = false;
        CarpetExtraSettings.dispensersStripBlocks = false;
        CarpetExtraSettings.dispensersTillSoil = false;
        CarpetExtraSettings.dispensersUseCauldrons = false;
    }
    
    @GameTest(templateName = STRUCTURE, batchId = BATCH)
    public void shearPumpkin(TestContext ctx) {
        blockConversionTest(ctx, Items.SHEARS, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, 1, false, () -> ctx.expectItem(Items.PUMPKIN_SEEDS));
    }

    @GameTest(templateName = STRUCTURE, batchId = BATCH)
    public void shearPumpkinBreaks(TestContext ctx) {
        blockConversionTest(ctx, Items.SHEARS, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, 1, true, () -> ctx.expectItem(Items.PUMPKIN_SEEDS));
    }

    @CustomTestProvider
    public Collection<TestFunction> stripTests() {
        List<TestFunction> fns = new ArrayList<>();
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
    
    @CustomTestProvider
    public Collection<TestFunction> cauldronTests() {
        List<TestFunction> fns = new ArrayList<>();
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

    @CustomTestProvider
    public Collection<TestFunction> tillTests() {
        List<TestFunction> fns = new ArrayList<>();
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
    
    @GameTest(templateName = STRUCTURE, batchId = BATCH)
    public void fillMinecartChest(TestContext ctx) {
        cartTest(ctx, Items.CHEST, EntityType.CHEST_MINECART);
    }

    @GameTest(templateName = STRUCTURE, batchId = BATCH)
    public void fillMinecartHopper(TestContext ctx) {
        cartTest(ctx, Items.HOPPER, EntityType.HOPPER_MINECART);
    }

    @GameTest(templateName = STRUCTURE, batchId = BATCH)
    public void fillMinecartTnt(TestContext ctx) {
        cartTest(ctx, Items.TNT, EntityType.TNT_MINECART, () -> ctx.dontExpectEntity(EntityType.TNT));
    }

    @GameTest(templateName = STRUCTURE, batchId = BATCH)
    public void fillMinecartFurnace(TestContext ctx) {
        cartTest(ctx, Items.FURNACE, EntityType.FURNACE_MINECART);
    }
    
    private void cartTest(TestContext ctx, Item item, EntityType<?> entity, Runnable... extras) {
        putInDispenser(ctx, item.getDefaultStack());
        ctx.setBlockState(lapis.up(), Blocks.RAIL);
        ctx.spawnEntity(EntityType.MINECART, lapis.up());
        
        ctx.pushButton(button);
        ctx.addFinalTaskWithDuration(4, () -> {
            ctx.expectEntityAt(entity, lapis.up());
            ctx.dontExpectEntity(EntityType.MINECART);
            ctx.dontExpectEntity(EntityType.ITEM);
            runAll(extras);
        });
    }
    
    // Util
    private void putInDispenser(TestContext ctx, ItemStack item) {
        ctx.<DispenserBlockEntity>getBlockEntity(dispenser).addToFirstFreeSlot(item);
    }
    
    private void putAtOneDurability(TestContext ctx, Item item) {
        ItemStack stack = item.getDefaultStack();
        stack.setDamage(stack.getMaxDamage() - 1);
        putInDispenser(ctx, stack);
    }
    
    private void checkFirstSlotHas(TestContext ctx, Item item, boolean damaged) {
        ctx.<DispenserBlockEntity>checkBlockEntity(dispenser, 
                disp -> disp.getStack(0).getItem() == item && (!damaged || disp.getStack(0).isDamaged()), 
                () -> "Must have " + (damaged ? "damaged " : "") + item + " in dispenser");
    }
    
    private void runAll(Runnable... actions) {
        for (Runnable r : actions) r.run();
    }
    
    // Setup util
    private TestFunction makeDispenserTest(String name, Consumer<TestContext> runner) {
        name = name.replace("minecraft\\:", "");
        return new TestFunction(BATCH, BATCH + '.' + name, STRUCTURE, 20, 0, true, runner);
    }
}
