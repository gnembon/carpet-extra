package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.util.math.random.AbstractRandom;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CactusBlock.class)
public abstract class CactusBlock_fertilizerMixin implements Fertilizable
{
    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
    {
        int i = this.countCactusAbove(world, pos);
        int j = this.countCactusBelow(world, pos);
        return CarpetExtraSettings.betterBonemeal && i + j < 2 && world.getBlockState(pos.up(i + 1)).isAir();
    }
    
    @Override
    public boolean canGrow(World world, AbstractRandom random, BlockPos pos, BlockState state)
    {
        return true;
    }
    
    @Override
    public void grow(ServerWorld world, AbstractRandom random, BlockPos pos, BlockState state)
    {
        int i = this.countCactusAbove(world, pos);
        BlockPos growPos = pos.up(i + 1);
        world.setBlockState(growPos, Blocks.CACTUS.getDefaultState());
        state.scheduledTick(world, growPos, random);
    }
    
    private int countCactusAbove(BlockView world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.up(i + 1)).isOf(Blocks.CACTUS); ++i)
        {
        }
        return i;
    }
    
    private int countCactusBelow(BlockView world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.down(i + 1)).isOf(Blocks.CACTUS); ++i)
        {
        }
        return i;
    }
}
