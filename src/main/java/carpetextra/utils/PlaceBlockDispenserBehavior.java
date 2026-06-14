package carpetextra.utils;

import carpetextra.CarpetExtraSettings;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseCoralPlantTypeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import java.util.Collection;

public class PlaceBlockDispenserBehavior  extends DefaultDispenseItemBehavior {
    private static final PlaceBlockDispenserBehavior instance = new PlaceBlockDispenserBehavior();
    public static PlaceBlockDispenserBehavior getInstance() {return instance;}
    @Override
    public ItemStack execute(BlockSource blockPointer, ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!CarpetExtraSettings.dispenserPlacesBlocks || !(item instanceof BlockItem)) {
            return super.execute(blockPointer, itemStack);
        }
        Block block = ((BlockItem) item).getBlock();

        Direction facing = blockPointer.state().getValue(DispenserBlock.FACING);
        Direction.Axis axis = facing.getAxis();
        Level world = blockPointer.level();
        BlockPos pos = blockPointer.pos();

        final Direction ffacing = facing;

        if (usePlacementContext(item, block)) { // no offset
            BlockHitResult hitResult = new BlockHitResult(Vec3.atLowerCornerOf(pos.relative(facing, 2)), facing, pos, false); // offset
            BlockPlaceContext ipc = new BlockPlaceContext(world, null, InteractionHand.MAIN_HAND, itemStack, hitResult) {
                @Override
                public Direction getNearestLookingDirection() {
                    return ffacing;
                }

                @Override
                public Direction getHorizontalDirection() {
                    return ffacing.getAxis() == Direction.Axis.Y ? Direction.NORTH : ffacing;
                }

                @Override
                public Direction[] getNearestLookingDirections() {
                    return new Direction[] {getNearestLookingDirection(), Direction.UP, Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
                }
            };
            InteractionResult result = ((BlockItem) item).place(ipc);
            if (result.consumesAction()) {
                return itemStack;
            } else {
                return super.execute(blockPointer, itemStack);
            }
        }

        pos = pos.relative(facing);

        BlockState state = block.defaultBlockState();
        if (state == null) return super.execute(blockPointer, itemStack);
        Collection<Property<?>> properties = state.getProperties();

        if (block instanceof StairBlock) {
            facing = facing.getOpposite();
        }

        if (properties.contains(BlockStateProperties.FACING)) {
            state = state.setValue(BlockStateProperties.FACING, facing);
        } else if (properties.contains(BlockStateProperties.HORIZONTAL_FACING) && axis != Direction.Axis.Y) {
            state = state.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        } else if (properties.contains(BlockStateProperties.FACING_HOPPER) && axis != Direction.Axis.Y) {
            state = state.setValue(BlockStateProperties.FACING_HOPPER, facing);
        } else if (properties.contains(BlockStateProperties.AXIS)) {
            state = state.setValue(BlockStateProperties.AXIS, axis);
        } else if (properties.contains(BlockStateProperties.HORIZONTAL_AXIS)  && axis != Direction.Axis.Y) {
            state = state.setValue(BlockStateProperties.HORIZONTAL_AXIS, axis);
        }

        if (properties.contains(BlockStateProperties.HALF)) {
            state = state.setValue(BlockStateProperties.HALF, facing == Direction.UP ? Half.TOP : Half.BOTTOM);
        } else if (properties.contains(BlockStateProperties.SLAB_TYPE)) {
            state = state.setValue(BlockStateProperties.SLAB_TYPE, facing == Direction.DOWN ? SlabType.TOP : SlabType.BOTTOM);
        }

        if (properties.contains(BlockStateProperties.WATERLOGGED)) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, false);
        }

        if (block instanceof ObserverBlock) {
            state = state.setValue(ObserverBlock.POWERED, true);
        }

        if (block instanceof LeavesBlock) {
            state = state.setValue(BlockStateProperties.PERSISTENT, true);
        }

        BlockState currentBlockState = world.getBlockState(pos);
        FluidState currentFluidState = world.getFluidState(pos);
        if ((currentBlockState.isAir() || currentBlockState.is(BlockTags.REPLACEABLE)) && currentBlockState.getBlock() != block && state.canSurvive(world, pos)) {
            state = Block.updateFromNeighbourShapes(state, world, pos);
            boolean blockWasPlaced = world.setBlockAndUpdate(pos, state);
            block.setPlacedBy(world, pos, state, null, itemStack);
            world.neighborChanged(pos, state.getBlock(), null);
            BlockItem.updateCustomBlockEntityTag(world, null, pos, itemStack);
            /* copy contents, mark it dirty to save & update comparators */
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.applyComponentsFromItemStack(itemStack);
                blockEntity.setChanged();
            }
            if (currentFluidState.isSource() && block instanceof LiquidBlockContainer) {
                ((LiquidBlockContainer) block).placeLiquid(world, pos, state, currentFluidState);
            }
            SoundType soundType = state.getSoundType();
            world.playSound(null, pos, soundType.getPlaceSound(), SoundSource.BLOCKS, (soundType.getVolume() + 1.0F / 2.0F), soundType.getPitch() * 0.8F);
            if (blockWasPlaced) {
                itemStack.shrink(1);
                return itemStack;
            }
        }

        return super.execute(blockPointer, itemStack);
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
               block instanceof SeagrassBlock || block instanceof KelpBlock || block instanceof BaseCoralPlantTypeBlock;
    }
}
