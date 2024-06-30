package carpetextra.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockPlacer
{
    public static final ImmutableSet<Property<?>> WHITELISTED_PROPERTIES = ImmutableSet.of(
            Properties.INVERTED,
            Properties.OPEN,
            Properties.PERSISTENT,
            Properties.CAN_SUMMON,
            Properties.ATTACHMENT,
            Properties.AXIS,
            Properties.BED_PART,
            Properties.BLOCK_HALF,
            Properties.BLOCK_FACE,
            Properties.CHEST_TYPE,
            Properties.COMPARATOR_MODE,
            Properties.DOOR_HINGE,
            Properties.DOUBLE_BLOCK_HALF,
            Properties.ORIENTATION,
            Properties.RAIL_SHAPE,
            Properties.STRAIGHT_RAIL_SHAPE,
            Properties.SLAB_TYPE,
            Properties.STAIR_SHAPE,
            Properties.BITES,
            Properties.DELAY,
            Properties.NOTE,
            Properties.ROTATION
    );

    // Written by masa
    public static <T extends Comparable<T>> BlockState alternativeBlockPlacementV3(BlockState state, UseContext context)
    {
        int protocolValue = (int) (context.getHitVec().x - (double) context.getPos().getX()) - 2;

        if (protocolValue < 0)
        {
            return state;
        }

        @Nullable DirectionProperty property = getFirstDirectionProperty(state);

        if (property != null && property != Properties.VERTICAL_DIRECTION)
        {
            state = applyDirectionProperty(state, context, property, protocolValue);

            if (state == null)
            {
                return null;
            }

            // Consume the bits used for the facing
            protocolValue >>>= 3;
        }
        // Consume the lowest unused bit
        protocolValue >>>= 1;

        List<Property<?>> propList = new ArrayList<>(state.getBlock().getStateManager().getProperties());
        propList.sort(Comparator.comparing(Property::getName));

        try
        {
            for (Property<?> p : propList)
            {
                if ((p instanceof DirectionProperty) == false &&
                    WHITELISTED_PROPERTIES.contains(p))
                {
                    @SuppressWarnings("unchecked")
                    Property<T> prop = (Property<T>) p;
                    List<T> list = new ArrayList<>(prop.getValues());
                    list.sort(Comparable::compareTo);

                    int requiredBits = MathHelper.floorLog2(MathHelper.smallestEncompassingPowerOfTwo(list.size()));
                    int bitMask = ~(0xFFFFFFFF << requiredBits);
                    int valueIndex = protocolValue & bitMask;

                    if (valueIndex >= 0 && valueIndex < list.size())
                    {
                        T value = list.get(valueIndex);

                        if (state.get(prop).equals(value) == false &&
                            value != SlabType.DOUBLE) // don't allow duping slabs by forcing a double slab via the protocol
                        {
                            state = state.with(prop, value);
                        }

                        protocolValue >>>= requiredBits;
                    }
                }
            }
        }
        catch (Exception e)
        {
            // Exception
        }

        return state;
    }

    public static BlockState alternativeBlockPlacementV2(Block block, UseContext context)
    {
        Vec3d hitPos = context.getHitVec();
        BlockPos blockPos = context.getPos();
        double relativeHitX = hitPos.x - blockPos.getX();
        BlockState state = block.getPlacementState(context.getItemPlacementContext());

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
                facing = context.getEntity().getHorizontalFacing().getOpposite();
            }

            if (facing != origFacing && directionProp.getValues().contains(facing))
            {
                if (state.getBlock() instanceof BedBlock)
                {
                    BlockPos headPos = blockPos.offset(facing);

                    if (context.getWorld().getBlockState(headPos).canReplace(context.getItemPlacementContext()) == false)
                    {
                        return null;
                    }
                }

                state = state.with(directionProp, facing);
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

    private static BlockState applyDirectionProperty(BlockState state, UseContext context,
                                                     DirectionProperty property, int protocolValue)
    {
        Direction facingOrig = state.get(property);
        Direction facing = facingOrig;
        int decodedFacingIndex = (protocolValue & 0xF) >> 1;

        if (decodedFacingIndex == 6) // the opposite of the normal facing requested
        {
            facing = facing.getOpposite();
        }
        else if (decodedFacingIndex >= 0 && decodedFacingIndex <= 5)
        {
            facing = Direction.byId(decodedFacingIndex);

            if (property.getValues().contains(facing) == false)
            {
                facing = context.getEntity().getHorizontalFacing().getOpposite();
            }
        }

        if (facing != facingOrig && property.getValues().contains(facing))
        {
            if (state.getBlock() instanceof BedBlock)
            {
                BlockPos headPos = context.getPos().offset(facing);
                ItemPlacementContext ctx = context.getItemPlacementContext();

                if (context.getWorld().getBlockState(headPos).canReplace(ctx) == false)
                {
                    return null;
                }
            }

            state = state.with(property, facing);
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

    public static class UseContext
    {
        private final World world;
        private final BlockPos pos;
        private final Direction side;
        private final Vec3d hitVec;
        private final LivingEntity entity;
        private final Hand hand;
        @Nullable
        private final ItemPlacementContext itemPlacementContext;

        private UseContext(World world, BlockPos pos, Direction side, Vec3d hitVec,
                           LivingEntity entity, Hand hand, @Nullable ItemPlacementContext itemPlacementContext)
        {
            this.world = world;
            this.pos = pos;
            this.side = side;
            this.hitVec = hitVec;
            this.entity = entity;
            this.hand = hand;
            this.itemPlacementContext = itemPlacementContext;
        }

        public static UseContext from(ItemPlacementContext ctx, Hand hand)
        {
            Vec3d pos = ctx.getHitPos();
            return new UseContext(ctx.getWorld(), ctx.getBlockPos(), ctx.getSide(), new Vec3d(pos.x, pos.y, pos.z),
                    ctx.getPlayer(), hand, ctx);
        }

        public World getWorld()
        {
            return this.world;
        }

        public BlockPos getPos()
        {
            return this.pos;
        }

        public Direction getSide()
        {
            return this.side;
        }

        public Vec3d getHitVec()
        {
            return this.hitVec;
        }

        public LivingEntity getEntity()
        {
            return this.entity;
        }

        public Hand getHand()
        {
            return this.hand;
        }

        @Nullable
        public ItemPlacementContext getItemPlacementContext()
        {
            return this.itemPlacementContext;
        }
    }
}
