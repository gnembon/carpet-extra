package carpetextra.utils;

import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.Vec3;

public class BlockPlacer
{
    public static BlockState alternativeBlockPlacement(Block block, BlockPlaceContext context)
    {
        Vec3 hitPos = context.getClickLocation();
        BlockPos blockPos = context.getClickedPos();
        double relativeHitX = hitPos.x - blockPos.getX();
        BlockState state = block.getStateForPlacement(context);

        if (relativeHitX < 2 || state == null) // vanilla handling
            return null;

        // It would be nice if relativeHitX was adjusted in context to original range from 0.0 to 1.0,
        // since some blocks are actually using it.
        EnumProperty<Direction> directionProp = getFirstDirectionProperty(state);
        int protocolValue = ((int) relativeHitX - 2) / 2;

        if (directionProp != null)
        {
            Direction origFacing = state.getValue(directionProp);
            Direction facing = origFacing;
            int facingIndex = protocolValue & 0xF;

            if (facingIndex == 6) // the opposite of the normal facing requested
            {
                facing = facing.getOpposite();
            }
            else if (facingIndex >= 0 && facingIndex <= 5)
            {
                facing = Direction.from3DDataValue(facingIndex);
            }

            if (directionProp.getPossibleValues().contains(facing) == false)
            {
                facing = context.getPlayer().getDirection().getOpposite();
            }

            if (facing != origFacing && directionProp.getPossibleValues().contains(facing))
            {
                if (state.getBlock() instanceof BedBlock)
                {
                    BlockPos headPos = blockPos.relative(facing);

                    if (context.getLevel().getBlockState(headPos).canBeReplaced(context) == false)
                    {
                        return null;
                    }
                }

                state = state.setValue(directionProp, facing);
            }
        }
        else if (state.hasProperty(BlockStateProperties.AXIS))
        {
            Direction.Axis axis = Direction.Axis.VALUES[protocolValue % 3];
            state = state.setValue(BlockStateProperties.AXIS, axis);
        }

        protocolValue &= 0xFFFFFFF0;

        if (protocolValue >= 16)
        {
            if (block instanceof RepeaterBlock)
            {
                Integer delay = (protocolValue / 16);

                if (RepeaterBlock.DELAY.getPossibleValues().contains(delay))
                {
                    state = state.setValue(RepeaterBlock.DELAY, delay);
                }
            }
            else if (protocolValue == 16)
            {
                if (block instanceof ComparatorBlock)
                {
                    state = state.setValue(ComparatorBlock.MODE, ComparatorMode.SUBTRACT);
                }
                else if (state.hasProperty(BlockStateProperties.HALF) &&
                         state.getValue(BlockStateProperties.HALF) == Half.BOTTOM)
                {
                    state = state.setValue(BlockStateProperties.HALF, Half.TOP);
                }
                else if (state.hasProperty(BlockStateProperties.SLAB_TYPE) &&
                         state.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM)
                {
                    state = state.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
                }
            }
        }

        return state;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static EnumProperty<Direction> getFirstDirectionProperty(BlockState state)
    {
        for (Property<?> prop : state.getProperties())
        {
            if (prop instanceof EnumProperty<?> enumProperty)
            {
                if (enumProperty.getValueClass().equals(Direction.class))
                {
                    return (EnumProperty<Direction>) enumProperty;
                }
            }
        }

        return null;
    }
}
