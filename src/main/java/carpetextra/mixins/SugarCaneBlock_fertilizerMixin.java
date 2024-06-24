package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlock_fertilizerMixin implements Fertilizable
{
    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state)
    {
        int i = this.countSugarCaneAbove(world, pos);
        int j = this.countSugarCaneBelow(world, pos);
        return CarpetExtraSettings.betterBonemeal && i + j < 2 && world.getBlockState(pos.up(i + 1)).isAir();
    }
    
    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
    {
        return true;
    }
    
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
    {
        int i = this.countSugarCaneAbove(world, pos);
        world.setBlockState(pos.up(i + 1), Blocks.SUGAR_CANE.getDefaultState());
    }
    
    private int countSugarCaneAbove(BlockView world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.up(i + 1)).isOf(Blocks.SUGAR_CANE); ++i)
        {
        }
        return i;
    }
    
    private int countSugarCaneBelow(BlockView world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.down(i + 1)).isOf(Blocks.SUGAR_CANE); ++i)
        {
        }
        return i;
    }
}
