package carpetextra.helpers;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

// Credits: Skyrising
public class ObsidianBlock extends Block
{
    public ObsidianBlock(Settings settings)
    {
        super(settings);
    }
    
    @Override
    public boolean hasRandomTicks(BlockState state)
    {
        return CarpetExtraSettings.renewableLava;
    }

    @Override
    public void scheduledTick(BlockState blockState_1, ServerWorld serverWorld_1, BlockPos blockPos_1, Random random_1)
    {
        for (Direction dir : Direction.values())
        {
            FluidState neighbor = serverWorld_1.getFluidState(blockPos_1.offset(dir));
            if (neighbor.getFluid() != Fluids.LAVA || !neighbor.isStill())
                return;
        }
        if (random_1.nextInt(10) == 0)
        {
            serverWorld_1.setBlockState(blockPos_1, Blocks.LAVA.getDefaultState());
        }
    }
}
