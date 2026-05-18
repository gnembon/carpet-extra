package carpetextra.dispenser.behaviors;

import carpetextra.dispenser.DispenserBehaviorHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class CauldronEmptyingDispenserBehavior extends DispenserBehaviorHelper {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        // check if front block is cauldron and is full
        if(frontBlock instanceof AbstractCauldronBlock && ((AbstractCauldronBlock) frontBlock).isFull(frontBlockState)) {
            // lava
            if(frontBlock == Blocks.LAVA_CAULDRON) {
                setCauldron(world, frontBlockPos, SoundEvents.BUCKET_FILL_LAVA);
                return this.addOrDispense(pointer, stack, new ItemStack(Items.LAVA_BUCKET));
            }
            // water
            else if(frontBlock == Blocks.WATER_CAULDRON) {
                setCauldron(world, frontBlockPos, SoundEvents.BUCKET_FILL);
                return this.addOrDispense(pointer, stack, new ItemStack(Items.WATER_BUCKET));
            }
            // powder snow
            else if(frontBlock == Blocks.POWDER_SNOW_CAULDRON) {
                setCauldron(world, frontBlockPos, SoundEvents.BUCKET_FILL_POWDER_SNOW);
                return this.addOrDispense(pointer, stack, new ItemStack(Items.POWDER_SNOW_BUCKET));
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // set cauldron, play sound, emit game event
    private static void setCauldron(ServerLevel world, BlockPos pos, SoundEvent soundEvent) {
        world.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
        world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
    }
}
