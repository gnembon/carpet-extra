package carpetextra.dispenser.behaviors;

import java.util.function.Supplier;
import carpetextra.mixins.BoatItemAccessorMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.AbstractChestBoatEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class PlaceBoatOnIceDispenserBehavior extends FallibleItemDispenserBehavior {

    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        Direction dispenserFacing = pointer.state().get(DispenserBlock.FACING);

        double boatXPos = pointer.pos().getX() + (double) ((float) dispenserFacing.getOffsetX() * 1.125F);
        double boatYPos = pointer.pos().getY() + (double) ((float) dispenserFacing.getOffsetY() * 1.125F);
        double boatZPos = pointer.pos().getZ() + (double) ((float) dispenserFacing.getOffsetZ() * 1.125F);

        BlockPos frontBlockPos = pointer.pos().offset(dispenserFacing);
        Block frontBlock = world.getBlockState(frontBlockPos).getBlock();
        BlockPos blockBelowFrontBlockPos = frontBlockPos.down();
        BlockState blockBelowFrontState = world.getBlockState(blockBelowFrontBlockPos);

        if (frontBlock == Blocks.AIR && blockBelowFrontState.isIn(BlockTags.ICE)) {
            BoatItemAccessorMixin boatInfo = (BoatItemAccessorMixin) stack.getItem();
            EntityType<? extends AbstractBoatEntity> boatType = boatInfo.getType();
            AbstractBoatEntity boatEntity;

            if (this.isChestBoat(boatType)) {
                boatEntity = new ChestBoatEntity((EntityType<? extends ChestBoatEntity>) boatType, world, stack::getItem);
            } else {
                boatEntity = new BoatEntity((EntityType<? extends BoatEntity>) boatType, world, stack::getItem);
            }
            boatEntity.initPosition(boatXPos, boatYPos, boatZPos);
            /*
            if (boatInfo.isChest()) {
                boatEntity = new ChestBoatEntity(world, boatXPos, boatYPos, boatZPos);
            } else {
                boatEntity = new BoatEntity(world, boatXPos, boatYPos, boatZPos);
            }
            boatEntity.setVariant(boatInfo.getType());
             */

            boatEntity.setYaw(dispenserFacing.getPositiveHorizontalDegrees());
            world.spawnEntity(boatEntity);
            stack.decrement(1);
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    private boolean isChestBoat(EntityType<? extends AbstractBoatEntity> boatType)
    {
        if      (boatType.equals(EntityType.ACACIA_CHEST_BOAT))     { return true; }
        else if (boatType.equals(EntityType.OAK_CHEST_BOAT))        { return true; }
        else if (boatType.equals(EntityType.MANGROVE_CHEST_BOAT))   { return true; }
        else if (boatType.equals(EntityType.JUNGLE_CHEST_BOAT))     { return true; }
        else if (boatType.equals(EntityType.CHERRY_CHEST_BOAT))     { return true; }
        else if (boatType.equals(EntityType.SPRUCE_CHEST_BOAT))     { return true; }
        else if (boatType.equals(EntityType.BIRCH_CHEST_BOAT))      { return true; }
        else if (boatType.equals(EntityType.DARK_OAK_CHEST_BOAT))   { return true; }
        else if (boatType.equals(EntityType.BAMBOO_CHEST_RAFT))     { return true; }
        else return boatType.equals(EntityType.PALE_OAK_CHEST_BOAT);
    }
}
