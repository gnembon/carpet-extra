package carpetextra.dispenser.behaviors;

import carpetextra.mixins.BoatItemAccessorMixin;
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
        this.setSuccess(false);
        ServerWorld world = pointer.world();
        Direction facing = pointer.state().get(DispenserBlock.FACING);

        BoatItemAccessorMixin boatInfo = (BoatItemAccessorMixin) stack.getItem();
        EntityType<? extends AbstractBoatEntity> boatType = boatInfo.getType();

        Vec3d vec3d = pointer.centerPos();
        double d = 0.5625 + boatType.getWidth() / 2.0;
        double x = vec3d.getX() + facing.getOffsetX() * d;
        double y = vec3d.getY() + facing.getOffsetY() * 1.125F;
        double z = vec3d.getZ() + facing.getOffsetZ() * d;

        BlockPos frontPos = pointer.pos().offset(facing);
        boolean iceInFront = world.getBlockState(frontPos).isIn(BlockTags.ICE);
        boolean airInFront = world.getBlockState(frontPos).isAir();
        boolean iceBelowFront = world.getBlockState(frontPos.down()).isIn(BlockTags.ICE);

        if (!(iceInFront || (airInFront && iceBelowFront))) {
            return stack;
        }

        double h = iceInFront ? 1.0 : 0.0;

        AbstractBoatEntity boat = boatType.create(world, SpawnReason.DISPENSER);
        if (boat != null) {
            boat.initPosition(x, y + h, z);
            EntityType.copier(world, stack, null).accept(boat);
            boat.setYaw(facing.getPositiveHorizontalDegrees());
            world.spawnEntity(boat);
            stack.decrement(1);
            this.setSuccess(true);
        }

        return stack;
    }
}
