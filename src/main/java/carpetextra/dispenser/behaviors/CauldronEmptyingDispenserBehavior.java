package carpetextra.dispenser.behaviors;

import carpetextra.dispenser.DispenserBehaviorHelper;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

public class CauldronEmptyingDispenserBehavior extends DispenserBehaviorHelper {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.getWorld();
        BlockPos frontBlockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        // check if front block is cauldron and is full
        if(frontBlock instanceof AbstractCauldronBlock && ((AbstractCauldronBlock) frontBlock).isFull(frontBlockState)) {
            // lava
            if(frontBlock == Blocks.LAVA_CAULDRON) {
                setCauldron(world, frontBlockPos, SoundEvents.ITEM_BUCKET_FILL_LAVA);
                return this.addOrDispense(pointer, stack, new ItemStack(Items.LAVA_BUCKET));
            }
            // water
            else if(frontBlock == Blocks.WATER_CAULDRON) {
                setCauldron(world, frontBlockPos, SoundEvents.ITEM_BUCKET_FILL);
                return this.addOrDispense(pointer, stack, new ItemStack(Items.WATER_BUCKET));
            }
            // powder snow
            else if(frontBlock == Blocks.POWDER_SNOW_CAULDRON) {
                setCauldron(world, frontBlockPos, SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW);
                return this.addOrDispense(pointer, stack, new ItemStack(Items.POWDER_SNOW_BUCKET));
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // set cauldron, play sound, emit game event
    private static void setCauldron(ServerWorld world, BlockPos pos, SoundEvent soundEvent) {
        world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
    }
}
