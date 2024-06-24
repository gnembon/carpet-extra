package carpetextra.dispenser.behaviors;

import carpetextra.dispenser.DispenserBehaviorHelper;
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
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        if (frontBlock == Blocks.WATER_CAULDRON) {
            if (item == Items.POTION) {
                // check if it's a water bottle (https://minecraft.wiki/w/Potion#Base_potions)
                PotionContentsComponent potionContentsComponent = stack.get(DataComponentTypes.POTION_CONTENTS);
                if (potionContentsComponent != null && potionContentsComponent.matches(Potions.WATER)) {
                    // check if cauldron is not full
                    if (!((AbstractCauldronBlock) frontBlock).isFull(frontBlockState)) {
                        // increase cauldron level
                        int level = frontBlockState.get(LeveledCauldronBlock.LEVEL);
                        BlockState cauldronState = frontBlockState.with(LeveledCauldronBlock.LEVEL, level + 1);
                        setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BOTTLE_EMPTY, GameEvent.FLUID_PLACE);

                        // return glass bottle
                        return this.addOrDispense(pointer, stack, new ItemStack(Items.GLASS_BOTTLE));
                    }
                }
            }
            else if (item == Items.GLASS_BOTTLE) {
                // decrease cauldron level
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

                    // return undyed shulker box
                    return this.addOrDispense(pointer, stack, undyedShulkerBox);
                }
            }
            if (stack.isIn(ItemTags.DYEABLE)) {
                // check if dyeable item has color
                if (stack.contains(DataComponentTypes.DYED_COLOR)) {
                    // decrease cauldron level
                    LeveledCauldronBlock.decrementFluidLevel(frontBlockState, world, frontBlockPos);
                    // remove color
                    stack.remove(DataComponentTypes.DYED_COLOR);
                    // return undyed item
                    return stack;
                }
            }
            else if (item instanceof BannerItem) {
                // check if banner has layers (https://minecraft.wiki/w/Banner#Patterns)
                BannerPatternsComponent bannerPatternsComponent = stack.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
                if (!bannerPatternsComponent.layers().isEmpty()) {
                    // decrease cauldron level
                    LeveledCauldronBlock.decrementFluidLevel(frontBlockState, world, frontBlockPos);
                    // copy banner stack, set to one item
                    ItemStack cleanedBanner = stack.copy();
                    cleanedBanner.setCount(1);
                    // remove layer from banner
                    cleanedBanner.set(DataComponentTypes.BANNER_PATTERNS, bannerPatternsComponent.withoutTopLayer());
                    // return cleaned banner
                    return this.addOrDispense(pointer, stack, cleanedBanner);
                }
            }
        }
        else if(frontBlock == Blocks.CAULDRON && item == Items.POTION) {
            // check if it's a water bottle (https://minecraft.wiki/w/Potion#Base_potions)
            PotionContentsComponent potionContentsComponent = stack.get(DataComponentTypes.POTION_CONTENTS);
            if (potionContentsComponent != null && potionContentsComponent.matches(Potions.WATER)) {
                // increase cauldron level
                BlockState cauldronState = Blocks.WATER_CAULDRON.getDefaultState();
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BOTTLE_EMPTY, GameEvent.FLUID_PLACE);

                // return glass bottle
                return this.addOrDispense(pointer, stack, new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // set cauldron, play sound, emit game event
    private static void setCauldron(ServerWorld world, BlockPos pos, BlockState state, SoundEvent soundEvent, RegistryEntry.Reference<GameEvent> gameEvent) {
        world.setBlockState(pos, state);
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(null, gameEvent, pos);
    }

    public static boolean isWaterCauldronItem(ItemStack stack) {
        /* accept empty and water bottles, banners and dyeable items */
        Item item = stack.getItem();
        if (item == Items.GLASS_BOTTLE || item instanceof BannerItem)
            return true;
        if (item == Items.POTION) {
            PotionContentsComponent potionContentsComponent = stack.getComponents().get(DataComponentTypes.POTION_CONTENTS);
            return potionContentsComponent != null && potionContentsComponent.matches(Potions.WATER);
        }
        if (Block.getBlockFromItem(item) instanceof ShulkerBoxBlock) {
            return item != Items.SHULKER_BOX; // dyed Shulkers only
        }
        if (stack.isIn(ItemTags.DYEABLE)) {
            return stack.getComponents().contains(DataComponentTypes.DYED_COLOR);
        }
        return false;
    }
}
