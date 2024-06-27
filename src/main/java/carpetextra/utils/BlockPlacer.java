package carpetextra.utils;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.RepeaterBlock;

import net.minecraft.block.enums.ChestType;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class BlockPlacer
{
	private static boolean isBlockAttachableChest(Block originBlock, Direction facing, BlockPos checkPos, World world) {
		BlockState checkState = world.getBlockState(checkPos);
		if (checkState == null) {
			return false;
		}
		if (originBlock.equals(checkState.getBlock())) {
			return checkState.get(ChestBlock.FACING).equals(facing) && checkState.get(ChestBlock.CHEST_TYPE) == ChestType.SINGLE;
		}
		return false;
	}

    public static BlockState alternativeBlockPlacement(Block block, ItemPlacementContext context)
    {
        Vec3d hitPos = context.getHitPos();
        BlockPos blockPos = context.getBlockPos();
        double relativeHitX = hitPos.x - blockPos.getX();
        BlockState state = block.getPlacementState(context);

        if (relativeHitX < 2 || state == null) // vanilla handling
            return null;

        // It would be nice if relativeHitX was adjusted in context to original range from 0.0 to 1.0,
        // since some blocks are actually using it.
        DirectionProperty directionProp = getFirstDirectionProperty(state);
        int protocolValue = ((int) relativeHitX - 2) / 2;

        if (directionProp != null)
        {
            Direction origFacing = state.get(directionProp);
            Direction facing = origFacing;
            int facingIndex = protocolValue & 0xF;

            if (facingIndex == 6) // the opposite of the normal facing requested
            {
                facing = facing.getOpposite();
            }
            else if (facingIndex >= 0 && facingIndex <= 5)
            {
                facing = Direction.byId(facingIndex);
            }

            if (directionProp.getValues().contains(facing) == false)
            {
                facing = context.getPlayer().getHorizontalFacing().getOpposite();
            }

            if (facing != origFacing && directionProp.getValues().contains(facing))
            {
                if (state.getBlock() instanceof BedBlock)
                {
                    BlockPos headPos = blockPos.offset(facing);

                    if (context.getWorld().getBlockState(headPos).canReplace(context) == false)
                    {
                        return null;
                    }
                }

                state = state.with(directionProp, facing);
            }
	        if (block instanceof ChestBlock)
	        {
		        if (isBlockAttachableChest(block, facing, blockPos.offset(facing.rotateYClockwise()), context.getWorld())) {
			        state = state.with(ChestBlock.CHEST_TYPE, ChestType.LEFT);
		        }
		        else if (isBlockAttachableChest(block, facing, blockPos.offset(facing.rotateYCounterclockwise()), context.getWorld())) {
			        state = state.with(ChestBlock.CHEST_TYPE, ChestType.RIGHT);
		        }
			else {
		        	state = state.with(ChestBlock.CHEST_TYPE, ChestType.SINGLE);
			}
	        }
        }
        else if (state.contains(Properties.AXIS))
        {
            Direction.Axis axis = Direction.Axis.VALUES[protocolValue % 3];
            state = state.with(Properties.AXIS, axis);
        }

        protocolValue &= 0xFFFFFFF0;

        if (protocolValue >= 16)
        {
            if (block instanceof RepeaterBlock)
            {
                Integer delay = (protocolValue / 16);

                if (RepeaterBlock.DELAY.getValues().contains(delay))
                {
                    state = state.with(RepeaterBlock.DELAY, delay);
                }
            }
            else if (protocolValue == 16)
            {
                if (block instanceof ComparatorBlock)
                {
                    state = state.with(ComparatorBlock.MODE, ComparatorMode.SUBTRACT);
                }
                else if (state.contains(Properties.BLOCK_HALF) &&
                         state.get(Properties.BLOCK_HALF) == BlockHalf.BOTTOM)
                {
                    state = state.with(Properties.BLOCK_HALF, BlockHalf.TOP);
                }
                else if (state.contains(Properties.SLAB_TYPE) &&
                         state.get(Properties.SLAB_TYPE) == SlabType.BOTTOM)
                {
                    state = state.with(Properties.SLAB_TYPE, SlabType.TOP);
                }
            }
        }

        return state;
    }

    @Nullable
    public static DirectionProperty getFirstDirectionProperty(BlockState state)
    {
        for (Property<?> prop : state.getProperties())
        {
            if (prop instanceof DirectionProperty)
            {
                return (DirectionProperty) prop;
            }
        }

        return null;
    }
}
