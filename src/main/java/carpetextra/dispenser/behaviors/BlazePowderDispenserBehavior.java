package carpetextra.dispenser.behaviors;

import net.minecraft.block.*;
import net.minecraft.block.dispenser.*;
import net.minecraft.item.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class BlazePowderDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        if(frontBlock == Blocks.NETHER_WART) {
            int age = frontBlockState.get(NetherWartBlock.AGE);
            if(age < 3) {
                // grow netherwart one stage
                world.setBlockState(frontBlockPos, frontBlockState.with(NetherWartBlock.AGE, age + 1), Block.NOTIFY_LISTENERS);
                // green sparkles
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, frontBlockPos, 0);

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
