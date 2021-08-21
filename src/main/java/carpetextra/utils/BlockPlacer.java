package carpetextra.utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.ChestType;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockPlacer
{
    private static Boolean HasDirectionProperty(BlockState state)
    {
        //malilib code
        for (Property<?> prop : state.getProperties())
        {
            if (prop instanceof DirectionProperty)
            {
                return true;
            }
        }
        return false;
    }
    public static DirectionProperty getFirstDirectionProperty(BlockState state)
    {
        //malilib code
        for (Property<?> prop : state.getProperties())
        {
            if (prop instanceof DirectionProperty)
            {
                return (DirectionProperty) prop;
            }
        }
        return null;
    }
    private static Boolean IsBlockAttachableChest(Block originBlock,Direction Facing, BlockPos checkPos, World world)
    {
        BlockState checkState = world.getBlockState(checkPos);
        if ( checkState == null ) 
        {
	return false;
        }
        if( originBlock.getName().equals(checkState.getBlock().getName()) )
        {
        return checkState.get(ChestBlock.FACING).equals(Facing) && checkState.get(ChestBlock.CHEST_TYPE) == ChestType.SINGLE;
        }
        return false;
    }
    public static BlockState alternativeBlockPlacement(Block block, ItemPlacementContext context)//World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        //actual alternative block placement code

        Direction facing;
        Vec3d vec3d = context.getHitPos();
        BlockPos pos = context.getBlockPos();
        double hitX = vec3d.x - pos.getX();
        BlockState state = block.getDefaultState();
        if (hitX<2 || !HasDirectionProperty(state) ) // vanilla
            return null;
        DirectionProperty property = getFirstDirectionProperty(state);
        int code = (int)(hitX-2)/2;
        int FacingId = code % 16;
        //
        // now it would be great if hitX was adjusted in context to original range from 0.0 to 1.0
        // since its actually using it. Its private - maybe with Reflections?
        //
        PlayerEntity placer = context.getPlayer();
        World world = context.getWorld();
        facing = Direction.byId(FacingId);
        if (property.getValues().contains(facing) == false) 
        {
            facing = placer.getHorizontalFacing().getOpposite();
        }
        state = state.with(property, facing);
       
        //check blocks with additional states first
        if (block instanceof RepeaterBlock)
        {
            state = state
                    .with(RepeaterBlock.DELAY, MathHelper.clamp(code / 16, 1, 4))
                    .with(RepeaterBlock.LOCKED, Boolean.FALSE);
        }
        else if (block instanceof TrapdoorBlock)
        {
            state = state
                    .with(TrapdoorBlock.OPEN, Boolean.FALSE)
                    .with(TrapdoorBlock.HALF, (code >= 16) ? BlockHalf.TOP : BlockHalf.BOTTOM)
                    .with(TrapdoorBlock.OPEN, world.isReceivingRedstonePower(pos));
        }
        else if (block instanceof ComparatorBlock)
        {
            ComparatorMode m = (hitX >= 16)?ComparatorMode.SUBTRACT: ComparatorMode.COMPARE;
            state = state
                    .with(ComparatorBlock.POWERED, Boolean.FALSE)
                    .with(ComparatorBlock.MODE, m);
        }
        else if (block instanceof StairsBlock)
        {
            state = block.getPlacementState(context)//worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
                    .with(StairsBlock.FACING, facing)
                    .with(StairsBlock.HALF, ( hitX >= 16)?BlockHalf.TOP : BlockHalf.BOTTOM);
        }
        else if (block instanceof WallMountedBlock)
        {
	//unsupported
            return null; 
        }
        else if (block instanceof ChestBlock)
	//-x +x 
	//+x east -x west +z south -z north
        {
	if(facing == Direction.SOUTH) 
	{
	    if (IsBlockAttachableChest(block, facing, pos.west(), world))
	    {
	    return state.with(ChestBlock.CHEST_TYPE, ChestType.LEFT);
	    }
	    if (IsBlockAttachableChest(block, facing, pos.east(), world))
	    {
	    return state.with(ChestBlock.CHEST_TYPE, ChestType.RIGHT);
	    }
	}
	else if (facing == Direction.WEST)  //-z +z
	{
	    if (IsBlockAttachableChest(block, facing, pos.north(), world))
	    {
	    return state.with(ChestBlock.CHEST_TYPE, ChestType.LEFT);
	    }
	    if (IsBlockAttachableChest(block, facing, pos.south(),world))
	    {
	    return state.with(ChestBlock.CHEST_TYPE, ChestType.RIGHT);
	    }
	}
	else if (facing == Direction.NORTH) //+x -x
	{
	    if (IsBlockAttachableChest(block, facing, pos.east(), world))
	    {
	    return state.with(ChestBlock.CHEST_TYPE, ChestType.LEFT);
	    }
	    if (IsBlockAttachableChest(block, facing, pos.west(), world))
	    {
	    return state.with(ChestBlock.CHEST_TYPE, ChestType.RIGHT);
	    }
	}
	else if (facing == Direction.EAST) //+z -z
	{
	    if (IsBlockAttachableChest(block, facing, pos.south(), world))
	    {
	    return state.with(ChestBlock.CHEST_TYPE, ChestType.LEFT);
	    }
	    if (IsBlockAttachableChest(block, facing, pos.north(), world))
	    {
	    return state.with(ChestBlock.CHEST_TYPE, ChestType.RIGHT);
	    }
	}
	return state.with(ChestBlock.CHEST_TYPE, ChestType.SINGLE);
        }
        return state;
    }
}

