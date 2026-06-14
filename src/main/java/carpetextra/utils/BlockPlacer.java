package carpetextra.utils;

<<<<<<< HEAD
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
import org.jetbrains.annotations.Nullable;

public class BlockPlacer
{
    public static BlockState alternativeBlockPlacement(Block block, BlockPlaceContext context)
    {
        Vec3 hitPos = context.getClickLocation();
        BlockPos blockPos = context.getClickedPos();
        double relativeHitX = hitPos.x - blockPos.getX();
        BlockState state = block.getStateForPlacement(context);
=======
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class BlockPlacer
{
    public static BlockState alternativeBlockPlacement(Block block, ItemPlacementContext context)
    {
        Vec3d hitPos = context.getHitPos();
        BlockPos blockPos = context.getBlockPos();
        double relativeHitX = hitPos.x - blockPos.getX();
        BlockState state = block.getPlacementState(context);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

        if (relativeHitX < 2 || state == null) // vanilla handling
            return null;

        // It would be nice if relativeHitX was adjusted in context to original range from 0.0 to 1.0,
        // since some blocks are actually using it.
        EnumProperty<Direction> directionProp = getFirstDirectionProperty(state);
        int protocolValue = ((int) relativeHitX - 2) / 2;

        if (directionProp != null)
        {
<<<<<<< HEAD
            Direction origFacing = state.getValue(directionProp);
=======
            Direction origFacing = state.get(directionProp);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            Direction facing = origFacing;
            int facingIndex = protocolValue & 0xF;

            if (facingIndex == 6) // the opposite of the normal facing requested
            {
                facing = facing.getOpposite();
            }
            else if (facingIndex >= 0 && facingIndex <= 5)
            {
<<<<<<< HEAD
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
=======
                facing = Direction.byIndex(facingIndex);
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    {
                        return null;
                    }
                }

<<<<<<< HEAD
                state = state.setValue(directionProp, facing);
            }
        }
        else if (state.hasProperty(BlockStateProperties.AXIS))
        {
            Direction.Axis axis = Direction.Axis.VALUES[protocolValue % 3];
            state = state.setValue(BlockStateProperties.AXIS, axis);
=======
                state = state.with(directionProp, facing);
            }
        }
        else if (state.contains(Properties.AXIS))
        {
            Direction.Axis axis = Direction.Axis.VALUES[protocolValue % 3];
            state = state.with(Properties.AXIS, axis);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }

        protocolValue &= 0xFFFFFFF0;

        if (protocolValue >= 16)
        {
            if (block instanceof RepeaterBlock)
            {
                Integer delay = (protocolValue / 16);

<<<<<<< HEAD
                if (RepeaterBlock.DELAY.getPossibleValues().contains(delay))
                {
                    state = state.setValue(RepeaterBlock.DELAY, delay);
=======
                if (RepeaterBlock.DELAY.getValues().contains(delay))
                {
                    state = state.with(RepeaterBlock.DELAY, delay);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                }
            }
            else if (protocolValue == 16)
            {
                if (block instanceof ComparatorBlock)
                {
<<<<<<< HEAD
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
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
<<<<<<< HEAD
                if (enumProperty.getValueClass().equals(Direction.class))
=======
                if (enumProperty.getType().equals(Direction.class))
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                {
                    return (EnumProperty<Direction>) enumProperty;
                }
            }
        }

        return null;
    }
}
