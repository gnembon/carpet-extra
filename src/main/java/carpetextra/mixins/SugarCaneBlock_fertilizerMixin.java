package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlock_fertilizerMixin implements BonemealableBlock
{
    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state)
    {
        int i = this.countSugarCaneAbove(world, pos);
        int j = this.countSugarCaneBelow(world, pos);
        return CarpetExtraSettings.betterBonemeal && i + j < 2 && world.getBlockState(pos.above(i + 1)).isAir();
    }
    
    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state)
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        return true;
    }
    
    @Override
<<<<<<< HEAD
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state)
    {
        int i = this.countSugarCaneAbove(world, pos);
        world.setBlockAndUpdate(pos.above(i + 1), Blocks.SUGAR_CANE.defaultBlockState());
    }
    
    private int countSugarCaneAbove(BlockGetter world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.above(i + 1)).is(Blocks.SUGAR_CANE); ++i)
=======
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
    {
        int i = this.countSugarCaneAbove(world, pos);
        world.setBlockState(pos.up(i + 1), Blocks.SUGAR_CANE.getDefaultState());
    }
    
    private int countSugarCaneAbove(BlockView world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.up(i + 1)).isOf(Blocks.SUGAR_CANE); ++i)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        {
        }
        return i;
    }
    
<<<<<<< HEAD
    private int countSugarCaneBelow(BlockGetter world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.below(i + 1)).is(Blocks.SUGAR_CANE); ++i)
=======
    private int countSugarCaneBelow(BlockView world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.down(i + 1)).isOf(Blocks.SUGAR_CANE); ++i)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        {
        }
        return i;
    }
}
