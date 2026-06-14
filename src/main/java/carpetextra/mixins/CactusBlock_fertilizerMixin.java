package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
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
    {
        return true;
    }
    
    @Override
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
        {
        }
        return i;
    }
    
    private int countCactusBelow(BlockGetter world, BlockPos pos)
    {
        int i;
        for (i = 0; i < 2 && world.getBlockState(pos.below(i + 1)).is(Blocks.CACTUS); ++i)
        {
        }
        return i;
    }
}
