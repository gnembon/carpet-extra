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
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CactusBlock.class)
public abstract class CactusBlock_fertilizerMixin implements BonemealableBlock
{
    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state)
    {
        int i = this.countCactusAbove(world, pos);
        int j = this.countCactusBelow(world, pos);
        return CarpetExtraSettings.betterBonemeal && i + j < 2 && world.getBlockState(pos.above(i + 1)).isAir();
    }
    
    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state)
=======
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CactusBlock.class)
public abstract class CactusBlock_fertilizerMixin implements Fertilizable
{
    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state)
    {
        int i = this.countCactusAbove(world, pos);
        int j = this.countCactusBelow(world, pos);
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
        int i = this.countCactusAbove(world, pos);
        BlockPos growPos = pos.above(i + 1);
        world.setBlockAndUpdate(growPos, Blocks.CACTUS.defaultBlockState());
        state.tick(world, growPos, random);
    }
    
    private int countCactusAbove(BlockGetter world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.above(i + 1)).is(Blocks.CACTUS); ++i)
=======
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        {
        }
        return i;
    }
    
<<<<<<< HEAD
    private int countCactusBelow(BlockGetter world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.below(i + 1)).is(Blocks.CACTUS); ++i)
=======
    private int countCactusBelow(BlockView world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.down(i + 1)).isOf(Blocks.CACTUS); ++i)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        {
        }
        return i;
    }
}
