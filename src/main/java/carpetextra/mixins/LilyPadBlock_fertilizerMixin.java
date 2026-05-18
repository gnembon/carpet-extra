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
import net.minecraft.world.level.block.LilyPadBlock;
import net.minecraft.world.level.block.WaterloggedTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.swing.text.html.BlockView;

@Mixin(LilyPadBlock.class)
public abstract class LilyPadBlock_fertilizerMixin implements BonemealableBlock {


    @Shadow
    protected abstract boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos);

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return CarpetExtraSettings.betterBonemeal;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        if (!CarpetExtraSettings.betterBonemeal) return;
        BlockState blockState = Blocks.LILY_PAD.defaultBlockState();

        stopGrowth:
        for (int i = 0; i < 24; ++i) {
            BlockPos growPos = pos;
            for (int j = 0; j < i / 16; ++j) {
                growPos = growPos.offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (world.getBlockState(growPos).isCollisionShapeFullBlock(world, growPos)) {
                    continue stopGrowth;
                }
            }
            if (canGrowTo(growPos, world)) {
                world.setBlockAndUpdate(growPos, blockState);
                state.tick(world, growPos, random);
            }

        }

    }

    private boolean canGrowTo(BlockPos pos, ServerLevel world) {
        return this.mayPlaceOn(world.getBlockState(pos.below()), world, pos.below()) && world.getBlockState(pos).isAir();
    }
}
