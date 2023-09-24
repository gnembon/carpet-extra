package carpetextra.dispenser.behaviors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;

public class CarvePumpkinDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        Direction dispenserFacing = pointer.state().get(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().offset(dispenserFacing);
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        // check if uncarved pumpkin
        if(frontBlock == Blocks.PUMPKIN) {
            // set direction of carved pumpkin if dispenser is facing horizontal, otherwise use default
            BlockState pumpkinState = Blocks.CARVED_PUMPKIN.getDefaultState();
            if(dispenserFacing.getAxis().isHorizontal()) {
                pumpkinState = pumpkinState.with(CarvedPumpkinBlock.FACING, dispenserFacing.getOpposite());
            }

            // set pumpkin, play carve sound, drop pumpkin seeds, emit game event
            world.setBlockState(frontBlockPos, pumpkinState);
            world.playSound(null, frontBlockPos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            Block.dropStack(world, frontBlockPos, new ItemStack(Items.PUMPKIN_SEEDS, 4));
            world.emitGameEvent(null, GameEvent.SHEAR, frontBlockPos);

            // damage shears, remove if broken
            if(stack.damage(1, world.random, null)) {
                stack.setCount(0);
            }

            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
