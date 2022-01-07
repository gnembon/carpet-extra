package carpetextra.dispenser.behaviors;

import carpetextra.mixins.BoatItemAccessorMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class PlaceBoatOnIceDispenserBehavior extends FallibleItemDispenserBehavior {

    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.getWorld();
        Direction dispenserFacing = pointer.getBlockState().get(DispenserBlock.FACING);

        double boatXPos = pointer.getX() + (double) ((float) dispenserFacing.getOffsetX() * 1.125F);
        double boatYPos = pointer.getY() + (double) ((float) dispenserFacing.getOffsetY() * 1.125F);
        double boatZPos = pointer.getZ() + (double) ((float) dispenserFacing.getOffsetZ() * 1.125F);

        BlockPos frontBlockPos = pointer.getPos().offset(dispenserFacing);
        Block frontBlock = world.getBlockState(frontBlockPos).getBlock();
        BlockPos blockBelowFrontBlockPos = frontBlockPos.down();
        BlockState blockBelowFrontBlockState = world.getBlockState(blockBelowFrontBlockPos);
        Block blockBelowFrontBlock = blockBelowFrontBlockState.getBlock();

        if(frontBlock == Blocks.AIR && BlockTags.ICE.values().contains(blockBelowFrontBlock)) {
            BoatEntity boatEntity = new BoatEntity(world, boatXPos, boatYPos, boatZPos);
            boatEntity.setBoatType(((BoatItemAccessorMixin) stack.getItem()).getType());
            boatEntity.setYaw(dispenserFacing.asRotation());
            world.spawnEntity(boatEntity);
            stack.decrement(1);
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
