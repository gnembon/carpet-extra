package carpetextra.dispenser.behaviors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;

public class BlazePowderDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.getWorld();
        BlockPos frontBlockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        if(frontBlock == Blocks.NETHER_WART) {
            int age = frontBlockState.get(NetherWartBlock.AGE);
            if(age < 3) {
                // grow netherwart one stage
                world.setBlockState(frontBlockPos, frontBlockState.with(NetherWartBlock.AGE, age + 1), Block.NOTIFY_LISTENERS);
                // green sparkles
                world.syncWorldEvent(2005, frontBlockPos, 0);

                // decrement item and return
                stack.decrement(1);
                return stack;

            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
