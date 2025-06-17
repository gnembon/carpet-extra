package carpetextra.dispenser.behaviors;

import carpetextra.mixins.BoatItemAccessorMixin;
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
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
