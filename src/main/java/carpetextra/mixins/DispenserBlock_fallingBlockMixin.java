package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBlock.class)
public class DispenserBlock_fallingBlockMixin {

    // This is what happens to the code when a constructor is private and I just want to change the velocity before spawnEntity xD

    private static final Vec3d INITIAL_VELOCITY = new Vec3d(0.0, 0.1, 0.0);

    private void createFallingBlockWithVelocity(World world, BlockPos pos, BlockState state, Vec3d velocity) {
        FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(EntityType.FALLING_BLOCK, world);
        FallingBlockEntityAccessor fallingBlockAccessor = (FallingBlockEntityAccessor)fallingBlockEntity;
        fallingBlockAccessor.setBlockState(state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, false) : state);
        fallingBlockEntity.intersectionChecked = true;
        Vec3d vecPos = Vec3d.ofBottomCenter(pos);
        fallingBlockEntity.setPosition(vecPos);
        fallingBlockEntity.setVelocity(velocity);
        fallingBlockEntity.lastX = vecPos.x;
        fallingBlockEntity.lastY = vecPos.y;
        fallingBlockEntity.lastZ = vecPos.z;
        fallingBlockEntity.setFallingBlockPos(fallingBlockEntity.getBlockPos());
        world.setBlockState(pos, state.getFluidState().getBlockState(), Block.NOTIFY_ALL);
        world.spawnEntity(fallingBlockEntity);
    }

    @Inject(
            method = "scheduledTick",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random rand, CallbackInfo ci) {
        if (CarpetExtraSettings.fallingBlockDispensers) {
            Direction direction = state.get(DispenserBlock.FACING);
            BlockPos facingPos = pos.offset(direction);
            BlockState facingState = world.getBlockState(facingPos);
            if (!facingState.isAir() && (!facingState.isOf(Blocks.WATER) && !facingState.isOf(Blocks.LAVA) || facingState.getFluidState().isStill())) {
                Vec3d velocity = (state.isOf(Blocks.DROPPER) ? INITIAL_VELOCITY : INITIAL_VELOCITY.add(Vec3d.of((direction).getVector())));
                createFallingBlockWithVelocity(world, facingPos, facingState, velocity);
                ci.cancel();
            }
        }
    }
}
