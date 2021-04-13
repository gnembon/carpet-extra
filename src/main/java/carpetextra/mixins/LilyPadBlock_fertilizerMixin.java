package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(LilyPadBlock.class)
public abstract class LilyPadBlock_fertilizerMixin implements Fertilizable {
    @Shadow protected abstract boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos);

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return CarpetExtraSettings.betterBonemeal;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
    {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
    {
        if(!CarpetExtraSettings.betterBonemeal) return;
        BlockState blockState = Blocks.LILY_PAD.getDefaultState();

        stopGrowth:
        for(int i = 0; i < 24; ++i) {
            BlockPos growPos = pos;
            for (int j = 0; j < i / 16; ++j) {
                growPos = growPos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (world.getBlockState(growPos).isFullCube(world, growPos)) {
                    continue stopGrowth;
                }
            }
            if (canGrowTo(growPos,world)) {
                world.setBlockState(growPos,blockState);
                state.scheduledTick(world, growPos, random);
            }

        }

    }

    private boolean canGrowTo(BlockPos pos, BlockView world) {
        return this.canPlantOnTop(world.getBlockState(pos.down()), world, pos.down()) && world.getBlockState(pos).isAir();
    }
}
