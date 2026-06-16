package carpetextra.dispenser.behaviors;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

public class FillMinecartDispenserBehavior extends OptionalDispenseItemBehavior {
    private final EntityType<? extends AbstractMinecart> MINECART_TYPE;

    public FillMinecartDispenserBehavior(EntityType<? extends AbstractMinecart> minecartType) {
        this.MINECART_TYPE = minecartType;
    }

    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        AABB frontBlockBox = new AABB(frontBlockPos);

        // get non-mounted minecarts in front of dispenser
        List<Minecart> minecarts = world.getEntities(EntityTypes.MINECART, frontBlockBox, EntitySelector.ENTITY_NOT_BEING_RIDDEN);

        if(!minecarts.isEmpty()) {
            // choose a random minecart in front of dispenser to fill
            Minecart oldMinecart = minecarts.get(world.getRandom().nextInt(minecarts.size()));
            AbstractMinecart newMinecart = AbstractMinecart.createMinecart(world, oldMinecart.getX(), oldMinecart.getY(), oldMinecart.getZ(), this.MINECART_TYPE, EntitySpawnReason.DISPENSER, ItemStack.EMPTY, null);

            // Copy data from original minecart to new minecart
            // Possibly missing some things to copy here. Add more if needed
            newMinecart.setDeltaMovement(oldMinecart.getDeltaMovement());
            newMinecart.setXRot(oldMinecart.getXRot());
            newMinecart.setYRot(oldMinecart.getYRot());
            newMinecart.setCustomName(oldMinecart.getCustomName());
            newMinecart.setRemainingFireTicks(oldMinecart.getRemainingFireTicks());


            // remove old minecart, spawn new minecart
            oldMinecart.discard();
            world.addFreshEntity(newMinecart);

            // decrement item and return
            stack.shrink(1);
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
