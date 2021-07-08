package carpetextra.dispenser.behaviors;

import java.util.List;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class FillMinecartDispenserBehavior extends FallibleItemDispenserBehavior {
    private final AbstractMinecartEntity.Type MINECART_TYPE;

    public FillMinecartDispenserBehavior(AbstractMinecartEntity.Type minecartType) {
        this.MINECART_TYPE = minecartType;
    }

    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.getWorld();
        BlockPos frontBlockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        Box frontBlockBox = new Box(frontBlockPos);

        // get non-mounted minecarts in front of dispenser
        List<MinecartEntity> minecarts = world.getEntitiesByType(EntityType.MINECART, frontBlockBox, EntityPredicates.NOT_MOUNTED);

        if(!minecarts.isEmpty()) {
            // choose a random minecart in front of dispenser to fill
            MinecartEntity oldMinecart = minecarts.get(world.random.nextInt(minecarts.size()));
            AbstractMinecartEntity newMinecart = AbstractMinecartEntity.create(world, oldMinecart.getX(), oldMinecart.getY(), oldMinecart.getZ(), this.MINECART_TYPE);

            // Copy data from original minecart to new minecart
            // Possibly missing some things to copy here. Add more if needed
            newMinecart.setVelocity(oldMinecart.getVelocity());
            newMinecart.setPitch(oldMinecart.getPitch());
            newMinecart.setYaw(oldMinecart.getYaw());
            newMinecart.setCustomName(oldMinecart.getCustomName());
            newMinecart.setFireTicks(oldMinecart.getFireTicks());


            // remove old minecart, spawn new minecart
            oldMinecart.discard();
            world.spawnEntity(newMinecart);

            // decrement item and return
            stack.decrement(1);
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
