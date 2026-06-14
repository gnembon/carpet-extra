package carpetextra.dispenser.behaviors;

import carpetextra.dispenser.DispenserBehaviorHelper;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class CauldronWaterDispenserBehavior extends DispenserBehaviorHelper {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        Item item = stack.getItem();
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
=======
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

public class CauldronWaterDispenserBehavior extends DispenserBehaviorHelper {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        Item item = stack.getItem();
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        if (frontBlock == Blocks.WATER_CAULDRON) {
            if (isWaterBottle(stack)) {
                // check if cauldron is not full
                if (!((AbstractCauldronBlock) frontBlock).isFull(frontBlockState)) {
                    // increase cauldron level
<<<<<<< HEAD
                    int level = frontBlockState.getValue(LayeredCauldronBlock.LEVEL);
                    BlockState cauldronState = frontBlockState.setValue(LayeredCauldronBlock.LEVEL, level + 1);
                    setCauldron(world, frontBlockPos, cauldronState, SoundEvents.BOTTLE_EMPTY, GameEvent.FLUID_PLACE);
=======
                    int level = frontBlockState.get(LeveledCauldronBlock.LEVEL);
                    BlockState cauldronState = frontBlockState.with(LeveledCauldronBlock.LEVEL, level + 1);
                    setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BOTTLE_EMPTY, GameEvent.FLUID_PLACE);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

                    // return glass bottle
                    return this.addOrDispense(pointer, stack, new ItemStack(Items.GLASS_BOTTLE));
                }
            }
            else if (item == Items.GLASS_BOTTLE) {
                // decrease cauldron level
<<<<<<< HEAD
                LayeredCauldronBlock.lowerFillLevel(frontBlockState, world, frontBlockPos);
                // return water bottle
                return this.addOrDispense(pointer, stack, PotionContents.createItemStack(Items.POTION, Potions.WATER));
            }
            else if (Block.byItem(item) instanceof ShulkerBoxBlock) {
                // make sure item isn't plain shulker box
                if (item != Items.SHULKER_BOX) {
                    // decrease cauldron level
                    LayeredCauldronBlock.lowerFillLevel(frontBlockState, world, frontBlockPos);
                    // turn dyed shulker box into undyed shulker box
                    ItemStack undyedShulkerBox = stack.transmuteCopy(Blocks.SHULKER_BOX, 1);
=======
                LeveledCauldronBlock.decrementFluidLevel(frontBlockState, world, frontBlockPos);
                // return water bottle
                return this.addOrDispense(pointer, stack, PotionContentsComponent.createStack(Items.POTION, Potions.WATER));
            }
            else if (Block.getBlockFromItem(item) instanceof ShulkerBoxBlock) {
                // make sure item isn't plain shulker box
                if (item != Items.SHULKER_BOX) {
                    // decrease cauldron level
                    LeveledCauldronBlock.decrementFluidLevel(frontBlockState, world, frontBlockPos);
                    // turn dyed shulker box into undyed shulker box
                    ItemStack undyedShulkerBox = stack.copyComponentsToNewStack(Blocks.SHULKER_BOX, 1);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

                    // return undyed shulker box
                    return this.addOrDispense(pointer, stack, undyedShulkerBox);
                }
            }
<<<<<<< HEAD
            if (stack.is(ItemTags.DYEABLE)) {
                // check if dyeable item has color
                if (stack.has(DataComponents.DYED_COLOR)) {
                    // decrease cauldron level
                    LayeredCauldronBlock.lowerFillLevel(frontBlockState, world, frontBlockPos);
                    // remove color
                    stack.remove(DataComponents.DYED_COLOR);
=======
            if (stack.isIn(ItemTags.DYEABLE)) {
                // check if dyeable item has color
                if (stack.contains(DataComponentTypes.DYED_COLOR)) {
                    // decrease cauldron level
                    LeveledCauldronBlock.decrementFluidLevel(frontBlockState, world, frontBlockPos);
                    // remove color
                    stack.remove(DataComponentTypes.DYED_COLOR);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    // return undyed item
                    return stack;
                }
            }
            else if (item instanceof BannerItem) {
                // check if banner has layers (https://minecraft.wiki/w/Banner#Patterns)
<<<<<<< HEAD
                BannerPatternLayers bannerPatterns = stack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
                if (!bannerPatterns.layers().isEmpty()) {
                    // decrease cauldron level
                    LayeredCauldronBlock.lowerFillLevel(frontBlockState, world, frontBlockPos);
=======
                BannerPatternsComponent bannerPatterns = stack.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
                if (!bannerPatterns.layers().isEmpty()) {
                    // decrease cauldron level
                    LeveledCauldronBlock.decrementFluidLevel(frontBlockState, world, frontBlockPos);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    // copy banner stack, set to one item
                    ItemStack cleanedBanner = stack.copy();
                    cleanedBanner.setCount(1);
                    // remove layer from banner
<<<<<<< HEAD
                    cleanedBanner.set(DataComponents.BANNER_PATTERNS, bannerPatterns.removeLast());
=======
                    cleanedBanner.set(DataComponentTypes.BANNER_PATTERNS, bannerPatterns.withoutTopLayer());
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    // return cleaned banner
                    return this.addOrDispense(pointer, stack, cleanedBanner);
                }
            }
        }
        else if (frontBlock == Blocks.CAULDRON && isWaterBottle(stack)) {
            // increase cauldron level
<<<<<<< HEAD
            BlockState cauldronState = Blocks.WATER_CAULDRON.defaultBlockState();
            setCauldron(world, frontBlockPos, cauldronState, SoundEvents.BOTTLE_EMPTY, GameEvent.FLUID_PLACE);
=======
            BlockState cauldronState = Blocks.WATER_CAULDRON.getDefaultState();
            setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BOTTLE_EMPTY, GameEvent.FLUID_PLACE);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

            // return glass bottle
            return this.addOrDispense(pointer, stack, new ItemStack(Items.GLASS_BOTTLE));
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // set cauldron, play sound, emit game event
<<<<<<< HEAD
    private static void setCauldron(ServerLevel world, BlockPos pos, BlockState state, SoundEvent soundEvent, Holder.Reference<GameEvent> gameEvent) {
        world.setBlockAndUpdate(pos, state);
        world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(null, gameEvent, pos);
=======
    private static void setCauldron(ServerWorld world, BlockPos pos, BlockState state, SoundEvent soundEvent, RegistryEntry.Reference<GameEvent> gameEvent) {
        world.setBlockState(pos, state);
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(null, gameEvent, pos);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
    
    private static boolean isWaterBottle(ItemStack stack) {
        if (stack.getItem() != Items.POTION) {
            return false;
        }

<<<<<<< HEAD
        PotionContents content = stack.get(DataComponents.POTION_CONTENTS);
        return content != null && content.is(Potions.WATER);
=======
        PotionContentsComponent content = stack.get(DataComponentTypes.POTION_CONTENTS);
        return content != null && content.matches(Potions.WATER);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }

    public static boolean isWaterCauldronItem(ItemStack stack) {
        /* accept empty and water bottles, banners and dyeable items */
        Item item = stack.getItem();
        if (item == Items.GLASS_BOTTLE || item instanceof BannerItem || isWaterBottle(stack))
            return true;
<<<<<<< HEAD
        if (Block.byItem(item) instanceof ShulkerBoxBlock) {
            return item != Items.SHULKER_BOX; // dyed Shulkers only
        }
        if (stack.is(ItemTags.DYEABLE)) {
            return stack.getComponents().has(DataComponents.DYED_COLOR);
=======
        if (Block.getBlockFromItem(item) instanceof ShulkerBoxBlock) {
            return item != Items.SHULKER_BOX; // dyed Shulkers only
        }
        if (stack.isIn(ItemTags.DYEABLE)) {
            return stack.getComponents().contains(DataComponentTypes.DYED_COLOR);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
        return false;
    }
}
