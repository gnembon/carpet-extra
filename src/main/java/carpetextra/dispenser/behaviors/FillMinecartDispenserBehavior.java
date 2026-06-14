package carpetextra.dispenser.behaviors;

import java.util.List;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

public class FillMinecartDispenserBehavior extends OptionalDispenseItemBehavior {
    private final EntityType<? extends AbstractMinecart> MINECART_TYPE;

    public FillMinecartDispenserBehavior(EntityType<? extends AbstractMinecart> minecartType) {
=======

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class FillMinecartDispenserBehavior extends FallibleItemDispenserBehavior {
    private final EntityType<? extends AbstractMinecartEntity> MINECART_TYPE;

    public FillMinecartDispenserBehavior(EntityType<? extends AbstractMinecartEntity> minecartType) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        this.MINECART_TYPE = minecartType;
    }

    @Override
<<<<<<< HEAD
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        AABB frontBlockBox = new AABB(frontBlockPos);

        // get non-mounted minecarts in front of dispenser
        List<Minecart> minecarts = world.getEntities(EntityType.MINECART, frontBlockBox, EntitySelector.ENTITY_NOT_BEING_RIDDEN);

        if(!minecarts.isEmpty()) {
            // choose a random minecart in front of dispenser to fill
            Minecart oldMinecart = minecarts.get(world.random.nextInt(minecarts.size()));
            AbstractMinecart newMinecart = AbstractMinecart.createMinecart(world, oldMinecart.getX(), oldMinecart.getY(), oldMinecart.getZ(), this.MINECART_TYPE, EntitySpawnReason.DISPENSER, ItemStack.EMPTY, null);

            // Copy data from original minecart to new minecart
            // Possibly missing some things to copy here. Add more if needed
            newMinecart.setDeltaMovement(oldMinecart.getDeltaMovement());
            newMinecart.setXRot(oldMinecart.getXRot());
            newMinecart.setYRot(oldMinecart.getYRot());
            newMinecart.setCustomName(oldMinecart.getCustomName());
            newMinecart.setRemainingFireTicks(oldMinecart.getRemainingFireTicks());
=======
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
        Box frontBlockBox = new Box(frontBlockPos);

        // get non-mounted minecarts in front of dispenser
        List<MinecartEntity> minecarts = world.getEntitiesByType(EntityType.MINECART, frontBlockBox, EntityPredicates.NOT_MOUNTED);

        if(!minecarts.isEmpty()) {
            // choose a random minecart in front of dispenser to fill
            MinecartEntity oldMinecart = minecarts.get(world.random.nextInt(minecarts.size()));
            AbstractMinecartEntity newMinecart = AbstractMinecartEntity.create(world, oldMinecart.getX(), oldMinecart.getY(), oldMinecart.getZ(), this.MINECART_TYPE, SpawnReason.DISPENSER, ItemStack.EMPTY, null);

            // Copy data from original minecart to new minecart
            // Possibly missing some things to copy here. Add more if needed
            newMinecart.setVelocity(oldMinecart.getVelocity());
            newMinecart.setPitch(oldMinecart.getPitch());
            newMinecart.setYaw(oldMinecart.getYaw());
            newMinecart.setCustomName(oldMinecart.getCustomName());
            newMinecart.setFireTicks(oldMinecart.getFireTicks());
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf


            // remove old minecart, spawn new minecart
            oldMinecart.discard();
<<<<<<< HEAD
            world.addFreshEntity(newMinecart);

            // decrement item and return
            stack.shrink(1);
=======
            world.spawnEntity(newMinecart);

            // decrement item and return
            stack.decrement(1);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
