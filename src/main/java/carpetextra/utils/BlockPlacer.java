package carpetextra.utils;

import net.minecraft.block.*;
import net.minecraft.block.enums.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockPlacer {
	private static DirectionProperty getFirstDirectionProperty(BlockState state) {
		for (Property<?> prop : state.getProperties()) {
			if (prop instanceof DirectionProperty) {
				return (DirectionProperty) prop;
			}
		}
		return null;
	}
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
	private static boolean isBlockOffsetNotReplaceableBlock(BlockPos checkPos, Direction facing, World world){
		return !world.getBlockState(checkPos.offset(facing)).getMaterial().isReplaceable();
	}

	public static BlockState alternativeBlockPlacement(Block block, ItemPlacementContext context)//World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		//actual alternative block placement code

		Direction facing;
		Vec3d vec3d = context.getHitPos();
		BlockPos pos = context.getBlockPos();
		double hitX = vec3d.x - pos.getX();
		BlockState state = block.getDefaultState();
		DirectionProperty directionProperty = getFirstDirectionProperty(state);
		if (hitX < 2 || !(block instanceof AbstractRailBlock) && directionProperty == null) // vanilla
			return null;
		int code = (int) (hitX - 2) / 2;

		//
		// now it would be great if hitX was adjusted in context to original range from 0.0 to 1.0
		// since its actually using it. Its private - maybe with Reflections?
		//
		PlayerEntity placer = context.getPlayer();
		World world = context.getWorld();
		if (block instanceof AbstractRailBlock){
			RailShape shapeEnumFound = RailShape.values()[code];
			if (block instanceof RailBlock)
			{
				return state.with(RailBlock.SHAPE,shapeEnumFound);
			}
			else if (block instanceof DetectorRailBlock)
			{
				return state.with(DetectorRailBlock.SHAPE,shapeEnumFound);
			}
			else
			{
				return state.with(PoweredRailBlock.SHAPE,shapeEnumFound);
			}
		}
		else
		{
			int FacingId = code % 16;
			facing = Direction.byId(FacingId);
			if (!directionProperty.getValues().contains(facing)) {
				if (placer == null){
					facing = Direction.NORTH;
				} else {
					facing = placer.getHorizontalFacing().getOpposite();
				}
			}
			state = state.with(directionProperty, facing);
		}
		//check blocks with additional states first
		if (block instanceof RepeaterBlock) {
			state = state
				.with(RepeaterBlock.DELAY, MathHelper.clamp(code / 16, 1, 4))
				.with(RepeaterBlock.LOCKED, Boolean.FALSE);
		} else if (block instanceof TrapdoorBlock) {
			state = state
				.with(TrapdoorBlock.OPEN, Boolean.FALSE)
				.with(TrapdoorBlock.HALF, (code >= 16) ? BlockHalf.TOP : BlockHalf.BOTTOM)
				.with(TrapdoorBlock.OPEN, world.isReceivingRedstonePower(pos));
		} else if (block instanceof ComparatorBlock) {
			ComparatorMode m = (hitX >= 16) ? ComparatorMode.SUBTRACT : ComparatorMode.COMPARE;
			state = state
				.with(ComparatorBlock.POWERED, Boolean.FALSE)
				.with(ComparatorBlock.MODE, m);
		} else if (block instanceof StairsBlock) {
			state = block.getPlacementState(context); //worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
			if(state == null) {return null;}
				return state.with(StairsBlock.FACING, facing)
				.with(StairsBlock.HALF, (hitX >= 16) ? BlockHalf.TOP : BlockHalf.BOTTOM);
		} else if (block instanceof WallMountedBlock) {
			return state.with(WallMountedBlock.FACE, (code >= 16) ? WallMountLocation.CEILING: WallMountLocation.FLOOR).
				with(WallMountedBlock.FACING, facing);
		} else if (block instanceof WallSkullBlock){
			return state.with(WallMountedBlock.FACING, facing);
		}
		else if (block instanceof ChestBlock)
		{
			if (isBlockAttachableChest(block, facing, pos.offset(facing.rotateYClockwise()), world)) {
				return state.with(ChestBlock.CHEST_TYPE, ChestType.LEFT);
			}
			else if (isBlockAttachableChest(block, facing, pos.offset(facing.rotateYCounterclockwise()), world)) {
				return state.with(ChestBlock.CHEST_TYPE, ChestType.RIGHT);
			}
			return state.with(ChestBlock.CHEST_TYPE, ChestType.SINGLE);
		} else if (block instanceof BedBlock && isBlockOffsetNotReplaceableBlock(pos, facing, world)) {
			return null;
		} else if (block instanceof DoorBlock && isBlockOffsetNotReplaceableBlock(pos, Direction.UP, world)) {
			return null;
		}
		return state;
	}
}

