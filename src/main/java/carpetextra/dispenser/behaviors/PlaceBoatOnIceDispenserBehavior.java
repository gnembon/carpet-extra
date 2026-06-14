package carpetextra.dispenser.behaviors;

import carpetextra.mixins.BoatItemAccessorMixin;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class PlaceBoatOnIceDispenserBehavior extends OptionalDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        Direction facing = pointer.state().getValue(DispenserBlock.FACING);
        
        BoatItemAccessorMixin boatInfo = (BoatItemAccessorMixin) stack.getItem();
        EntityType<? extends AbstractBoat> boatType = boatInfo.getType();

        Vec3 vec3d = pointer.center();
        double d = 0.5625 + boatType.getWidth() / 2.0;
        double x = vec3d.x() + facing.getStepX() * d;
        double y = vec3d.y() + facing.getStepY() * 1.125F;
        double z = vec3d.z() + facing.getStepZ() * d;

        BlockPos frontBlockPos = pointer.pos().relative(facing);
        Block frontBlock = world.getBlockState(frontBlockPos).getBlock();
        BlockState stateBelowFront = world.getBlockState(frontBlockPos.below());

        if (frontBlock == Blocks.AIR && stateBelowFront.is(BlockTags.ICE)) {
            AbstractBoat boatEntity = boatType.create(world, EntitySpawnReason.DISPENSER);

            boatEntity.setInitialPos(x, y, z);
            EntityType.createDefaultStackConfig(world, stack, null).accept(boatEntity);
            boatEntity.setYRot(facing.toYRot());

            world.addFreshEntity(boatEntity);
            stack.shrink(1);
=======
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class PlaceBoatOnIceDispenserBehavior extends FallibleItemDispenserBehavior {

    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        Direction facing = pointer.state().get(DispenserBlock.FACING);
        
        BoatItemAccessorMixin boatInfo = (BoatItemAccessorMixin) stack.getItem();
        EntityType<? extends AbstractBoatEntity> boatType = boatInfo.getType();

        Vec3d vec3d = pointer.centerPos();
        double d = 0.5625 + boatType.getWidth() / 2.0;
        double x = vec3d.getX() + facing.getOffsetX() * d;
        double y = vec3d.getY() + facing.getOffsetY() * 1.125F;
        double z = vec3d.getZ() + facing.getOffsetZ() * d;

        BlockPos frontBlockPos = pointer.pos().offset(facing);
        Block frontBlock = world.getBlockState(frontBlockPos).getBlock();
        BlockState stateBelowFront = world.getBlockState(frontBlockPos.down());

        if (frontBlock == Blocks.AIR && stateBelowFront.isIn(BlockTags.ICE)) {
            AbstractBoatEntity boatEntity = boatType.create(world, SpawnReason.DISPENSER);

            boatEntity.initPosition(x, y, z);
            EntityType.copier(world, stack, null).accept(boatEntity);
            boatEntity.setYaw(facing.getPositiveHorizontalDegrees());

            world.spawnEntity(boatEntity);
            stack.decrement(1);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
