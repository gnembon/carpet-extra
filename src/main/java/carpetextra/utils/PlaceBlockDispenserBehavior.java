package carpetextra.utils;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.ObserverBlock;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.block.SeagrassBlock;
import net.minecraft.block.KelpBlock;
import net.minecraft.block.CoralParentBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Collection;

public class PlaceBlockDispenserBehavior  extends ItemDispenserBehavior {
    private static PlaceBlockDispenserBehavior instance = new PlaceBlockDispenserBehavior();
    public static PlaceBlockDispenserBehavior getInstance() {return instance;}
    @Override
    public ItemStack dispenseSilently(BlockPointer blockPointer, ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!CarpetExtraSettings.dispenserPlacesBlocks || !(item instanceof BlockItem)) {
            return super.dispenseSilently(blockPointer, itemStack);
        }
        Block block = ((BlockItem) item).getBlock();

        Direction facing = blockPointer.getBlockState().get(DispenserBlock.FACING);
        Direction.Axis axis = facing.getAxis();
        World world = blockPointer.getWorld();
        BlockPos pos = blockPointer.getPos();

        final Direction ffacing = facing;

        if (usePlacementContext(item, block)) { // no offset
            BlockHitResult hitResult = new BlockHitResult(Vec3d.of(pos.offset(facing, 2)), facing, pos, false); // offset
            ItemPlacementContext ipc = new ItemPlacementContext(world, null, Hand.MAIN_HAND, itemStack, hitResult) {
                @Override
                public Direction getPlayerLookDirection() {
                    return ffacing;
                }

                @Override
                public Direction getPlayerFacing() {
                    return ffacing.getAxis() == Direction.Axis.Y ? Direction.NORTH : ffacing;
                }

                @Override
                public Direction[] getPlacementDirections() {
                    return new Direction[] {getPlayerLookDirection(), Direction.UP, Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
                }
            };
            ActionResult result = ((BlockItem) item).place(ipc);
            if (result.isAccepted()) {
                return itemStack;
            } else {
                return super.dispenseSilently(blockPointer, itemStack);
            }
        }

        pos = pos.offset(facing);

        BlockState state = block.getDefaultState();
        if (state == null) return super.dispenseSilently(blockPointer, itemStack);
        Collection<Property<?>> properties = state.getProperties();

        if (block instanceof StairsBlock) {
            facing = facing.getOpposite();
        }

        if (properties.contains(Properties.FACING)) {
            state = state.with(Properties.FACING, facing);
        } else if (properties.contains(Properties.HORIZONTAL_FACING) && axis != Direction.Axis.Y) {
            state = state.with(Properties.HORIZONTAL_FACING, facing);
        } else if (properties.contains(Properties.HOPPER_FACING) && axis != Direction.Axis.Y) {
            state = state.with(Properties.HOPPER_FACING, facing);
        } else if (properties.contains(Properties.AXIS)) {
            state = state.with(Properties.AXIS, axis);
        } else if (properties.contains(Properties.HORIZONTAL_AXIS)  && axis != Direction.Axis.Y) {
            state = state.with(Properties.HORIZONTAL_AXIS, axis);
        }

        if (properties.contains(Properties.BLOCK_HALF)) {
            state = state.with(Properties.BLOCK_HALF, facing == Direction.UP ? BlockHalf.TOP : BlockHalf.BOTTOM);
        } else if (properties.contains(Properties.SLAB_TYPE)) {
            state = state.with(Properties.SLAB_TYPE, facing == Direction.DOWN ? SlabType.TOP : SlabType.BOTTOM);
        }

        if (properties.contains(Properties.WATERLOGGED)) {
            state = state.with(Properties.WATERLOGGED, false);
        }

        if (block instanceof ObserverBlock) {
            state = state.with(ObserverBlock.POWERED, true);
        }

        if (block instanceof LeavesBlock) {
            state = state.with(Properties.PERSISTENT, true);
        }

        BlockState currentBlockState = world.getBlockState(pos);
        FluidState currentFluidState = world.getFluidState(pos);
        if ((currentBlockState.isAir() || currentBlockState.getMaterial().isReplaceable()) && currentBlockState.getBlock() != block && state.canPlaceAt(world, pos)) {
            state = Block.postProcessState(state, world, pos);
            boolean blockWasPlaced = world.setBlockState(pos, state);
            block.onPlaced(world, pos, state, null, itemStack);
            world.updateNeighbor(pos, state.getBlock(), pos);
            NbtCompound blockEntityTag = itemStack.getSubNbt("BlockEntityTag");
            if (blockEntityTag != null && block instanceof BlockEntityProvider) {
                BlockEntity be = world.getBlockEntity(pos);
                blockEntityTag = new NbtCompound().copyFrom(blockEntityTag);
                blockEntityTag.putInt("x", pos.getX());
                blockEntityTag.putInt("y", pos.getY());
                blockEntityTag.putInt("z", pos.getZ());
                be.readNbt(blockEntityTag);
            }
            if (currentFluidState.isStill() && block instanceof FluidFillable) {
                ((FluidFillable) block).tryFillWithFluid(world, pos, state, currentFluidState);
            }
            BlockSoundGroup soundType = state.getSoundGroup();
            world.playSound(null, pos, soundType.getPlaceSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F / 2.0F), soundType.getPitch() * 0.8F);
            if (blockWasPlaced) {
                itemStack.decrement(1);
                return itemStack;
            }
        }

        return super.dispenseSilently(blockPointer, itemStack);
    }

    public static boolean canPlace(Block block) {
        if (CarpetExtraSettings.dispenserPlacesBlocks) {
            // extra exceptions
            return true;
        }
        return false;
    }

    private static boolean usePlacementContext(Item item, Block block) {
        return item.getClass() != BlockItem.class || block instanceof SeaPickleBlock || block instanceof TurtleEggBlock ||
               block instanceof SeagrassBlock || block instanceof KelpBlock || block instanceof CoralParentBlock;
    }
}
