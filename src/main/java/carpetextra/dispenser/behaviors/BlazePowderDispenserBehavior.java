package carpetextra.dispenser.behaviors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlazePowderDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        if(frontBlock == Blocks.NETHER_WART) {
            int age = frontBlockState.getValue(NetherWartBlock.AGE);
            if(age < 3) {
                // grow netherwart one stage
                world.setBlock(frontBlockPos, frontBlockState.setValue(NetherWartBlock.AGE, age + 1), Block.UPDATE_CLIENTS);
                // green sparkles
                world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, frontBlockPos, 0);

                // decrement item and return
                stack.shrink(1);
                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
