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
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WaterlilyBlock.class)
public abstract class LilyPadBlock_fertilizerMixin implements BonemealableBlock {
    @Shadow protected abstract boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos);

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
=======
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LilyPadBlock.class)
public abstract class LilyPadBlock_fertilizerMixin implements Fertilizable {
    @Shadow protected abstract boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos);

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        return CarpetExtraSettings.betterBonemeal;
    }

    @Override
<<<<<<< HEAD
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state)
=======
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        return true;
    }

    @Override
<<<<<<< HEAD
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state)
    {
        if(!CarpetExtraSettings.betterBonemeal) return;
        BlockState blockState = Blocks.LILY_PAD.defaultBlockState();
=======
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
    {
        if(!CarpetExtraSettings.betterBonemeal) return;
        BlockState blockState = Blocks.LILY_PAD.getDefaultState();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

        stopGrowth:
        for(int i = 0; i < 24; ++i) {
            BlockPos growPos = pos;
            for (int j = 0; j < i / 16; ++j) {
<<<<<<< HEAD
                growPos = growPos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (world.getBlockState(growPos).isCollisionShapeFullBlock(world, growPos)) {
=======
                growPos = growPos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (world.getBlockState(growPos).isFullCube(world, growPos)) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    continue stopGrowth;
                }
            }
            if (canGrowTo(growPos,world)) {
<<<<<<< HEAD
                world.setBlockAndUpdate(growPos,blockState);
                state.tick(world, growPos, random);
=======
                world.setBlockState(growPos,blockState);
                state.scheduledTick(world, growPos, random);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            }

        }

    }

<<<<<<< HEAD
    private boolean canGrowTo(BlockPos pos, BlockGetter world) {
        return this.mayPlaceOn(world.getBlockState(pos.below()), world, pos.below()) && world.getBlockState(pos).isAir();
=======
    private boolean canGrowTo(BlockPos pos, BlockView world) {
        return this.canPlantOnTop(world.getBlockState(pos.down()), world, pos.down()) && world.getBlockState(pos).isAir();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
