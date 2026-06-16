package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBlock.class)
public class DispenserBlock_fallingBlockMixin {

    // This is what happens to the code when a constructor is private and I just want to change the velocity before spawnEntity xD

    private static final Vec3 INITIAL_VELOCITY = new Vec3(0.0, 0.1, 0.0);

    private void createFallingBlockWithVelocity(Level world, BlockPos pos, BlockState state, Vec3 velocity) {
        FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(EntityTypes.FALLING_BLOCK, world);
        FallingBlockEntityAccessor fallingBlockAccessor = (FallingBlockEntityAccessor)fallingBlockEntity;
        fallingBlockAccessor.setBlockState(state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, false) : state);
        fallingBlockEntity.blocksBuilding = true;
        Vec3 vecPos = Vec3.atBottomCenterOf(pos);
        fallingBlockEntity.setPos(vecPos);
        fallingBlockEntity.setDeltaMovement(velocity);
        fallingBlockEntity.xo = vecPos.x;
        fallingBlockEntity.yo = vecPos.y;
        fallingBlockEntity.zo = vecPos.z;
        fallingBlockEntity.setStartPos(fallingBlockEntity.blockPosition());
        world.setBlock(pos, state.getFluidState().createLegacyBlock(), Block.UPDATE_ALL);
        world.addFreshEntity(fallingBlockEntity);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onScheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand, CallbackInfo ci) {
        if (CarpetExtraSettings.fallingBlockDispensers) {
            Direction direction = state.getValue(DispenserBlock.FACING);
            BlockPos facingPos = pos.relative(direction);
            BlockState facingState = world.getBlockState(facingPos);
            if (!facingState.isAir() && (!facingState.is(Blocks.WATER) && !facingState.is(Blocks.LAVA) || facingState.getFluidState().isSource())) {
                Vec3 velocity = (state.is(Blocks.DROPPER) ? INITIAL_VELOCITY : INITIAL_VELOCITY.add(Vec3.atLowerCornerOf((direction).getUnitVec3i())));
                createFallingBlockWithVelocity(world, facingPos, facingState, velocity);
                ci.cancel();
            }
        }
    }
}
