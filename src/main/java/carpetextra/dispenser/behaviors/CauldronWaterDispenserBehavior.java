package carpetextra.dispenser.behaviors;

import carpetextra.dispenser.DispenserBehaviorHelper;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
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
        ServerWorld world = pointer.getWorld();
        BlockPos frontBlockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        if(frontBlock == Blocks.WATER_CAULDRON) {
            if(item == Items.POTION && PotionUtil.getPotion(stack) == Potions.WATER) {
                // check if cauldron is not full
                if(!((AbstractCauldronBlock) frontBlock).isFull(frontBlockState)) {
                    // increase cauldron level
                    int level = frontBlockState.get(LeveledCauldronBlock.LEVEL);
                    BlockState cauldronState = frontBlockState.with(LeveledCauldronBlock.LEVEL, level + 1);
                    setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BOTTLE_EMPTY, GameEvent.FLUID_PLACE);

                    // return glass bottle
                    return this.addOrDispense(pointer, stack, new ItemStack(Items.GLASS_BOTTLE));
                }
            }
            else if(item == Items.GLASS_BOTTLE) {
                // decrease cauldron level
                LeveledCauldronBlock.decrementFluidLevel(frontBlockState, world, frontBlockPos);
                // return water bottle
                return this.addOrDispense(pointer, stack, PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER));
            }
            else if(Block.getBlockFromItem(item) instanceof ShulkerBoxBlock) {
                // make sure item isn't plain shulker box
                if(item != Items.SHULKER_BOX) {
                    // decrease cauldron level
                    LeveledCauldronBlock.decrementFluidLevel(frontBlockState, world, frontBlockPos);
                    // turn dyed shulker box into undyed shulker box
                    ItemStack undyedShulkerBox = new ItemStack(Items.SHULKER_BOX);
                    if(stack.hasNbt()) {
                        undyedShulkerBox.setNbt(stack.getNbt().copy());
                    }

                    // return undyed shulker box
                    return this.addOrDispense(pointer, stack, undyedShulkerBox);
                }
            }
            if(item instanceof DyeableItem) {
                DyeableItem dyeableItem = (DyeableItem) item;

                // check if dyeable item has color
                if(dyeableItem.hasColor(stack)) {
                    // decrease cauldron level
                    LeveledCauldronBlock.decrementFluidLevel(frontBlockState, world, frontBlockPos);
                    // remove color
                    dyeableItem.removeColor(stack);

                    // return undyed item
                    return stack;
                }
            }
            else if(item instanceof BannerItem) {
                // checks if banner has layers
                if(BannerBlockEntity.getPatternCount(stack) > 0) {
                    // decrease cauldron level
                    LeveledCauldronBlock.decrementFluidLevel(frontBlockState, world, frontBlockPos);
                    // copy banner stack, set to one item
                    ItemStack cleanedBanner = stack.copy();
                    cleanedBanner.setCount(1);
                    // removes layer from banner (yarn name is misleading)
                    BannerBlockEntity.loadFromItemStack(cleanedBanner);

                    // return cleaned banner
                    return this.addOrDispense(pointer, stack, cleanedBanner);
                }
            }
        }
        else if(frontBlock == Blocks.CAULDRON && item == Items.POTION && PotionUtil.getPotion(stack) == Potions.WATER) {
            // increase cauldron level
            BlockState cauldronState = Blocks.WATER_CAULDRON.getDefaultState();
            setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BOTTLE_EMPTY, GameEvent.FLUID_PLACE);

            // return glass bottle
            return this.addOrDispense(pointer, stack, new ItemStack(Items.GLASS_BOTTLE));
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // set cauldron, play sound, emit game event
    private static void setCauldron(ServerWorld world, BlockPos pos, BlockState state, SoundEvent soundEvent, GameEvent gameEvent) {
        world.setBlockState(pos, state);
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(gameEvent, pos);
    }

    public static boolean isWaterCauldronItem(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.GLASS_BOTTLE ||
            // water bottle
            (item == Items.POTION && PotionUtil.getPotion(stack) == Potions.WATER) ||
            // shulker boxes
            Block.getBlockFromItem(item) instanceof ShulkerBoxBlock ||
            // banners
            item instanceof BannerItem ||
            // dyeable items (leather armor, leather horse armor)
            (item instanceof DyeableItem && ((DyeableItem) item).hasColor(stack));
    }
}
