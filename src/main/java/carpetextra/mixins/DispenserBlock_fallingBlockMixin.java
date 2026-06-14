package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBlock.class)
public class DispenserBlock_fallingBlockMixin {

    // This is what happens to the code when a constructor is private and I just want to change the velocity before spawnEntity xD

<<<<<<< HEAD
    private static final Vec3 INITIAL_VELOCITY = new Vec3(0.0, 0.1, 0.0);

    private void createFallingBlockWithVelocity(Level world, BlockPos pos, BlockState state, Vec3 velocity) {
        FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(EntityType.FALLING_BLOCK, world);
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
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                createFallingBlockWithVelocity(world, facingPos, facingState, velocity);
                ci.cancel();
            }
        }
    }
}
