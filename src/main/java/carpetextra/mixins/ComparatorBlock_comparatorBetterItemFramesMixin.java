package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.CarpetExtraSettings.ComparatorOptions;
<<<<<<< HEAD
=======
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

@Mixin(ComparatorBlock.class)
public abstract class ComparatorBlock_comparatorBetterItemFramesMixin extends DiodeBlock {
    protected ComparatorBlock_comparatorBetterItemFramesMixin(Properties settings) {
=======

@Mixin(ComparatorBlock.class)
public abstract class ComparatorBlock_comparatorBetterItemFramesMixin extends AbstractRedstoneGateBlock {
    protected ComparatorBlock_comparatorBetterItemFramesMixin(Settings settings) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        super(settings);
    }

    @Unique
<<<<<<< HEAD
    private ItemFrame getAttachedItemFrameHorizontal(Level world, Direction facing, BlockPos pos) {
        List<ItemFrame> list;
        list = world.getEntitiesOfClass(ItemFrame.class, new AABB(pos), (itemFrame) ->
                itemFrame != null && (CarpetExtraSettings.comparatorBetterItemFrames.ordinal() >= 2 || itemFrame.getDirection() == facing));
        if (list.isEmpty() && CarpetExtraSettings.comparatorBetterItemFrames == ComparatorOptions.EXTENDED) {
            list = world.getEntitiesOfClass(ItemFrame.class, new AABB(pos).inflate(0.3), Objects::nonNull);
=======
    private ItemFrameEntity getAttachedItemFrameHorizontal(World world, Direction facing, BlockPos pos) {
        List<ItemFrameEntity> list;
        list = world.getEntitiesByClass(ItemFrameEntity.class, new Box(pos), (itemFrame) ->
                itemFrame != null && (CarpetExtraSettings.comparatorBetterItemFrames.ordinal() >= 2 || itemFrame.getHorizontalFacing() == facing));
        if (list.isEmpty() && CarpetExtraSettings.comparatorBetterItemFrames == ComparatorOptions.EXTENDED) {
            list = world.getEntitiesByClass(ItemFrameEntity.class, new Box(pos).expand(0.3), Objects::nonNull);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Unique
<<<<<<< HEAD
    private ItemFrame getUnAttachedItemFrameHorizontal(Level world, Direction facing, BlockPos pos) {
        // If item frame is sitting in front of the comparator, horizontally or on another block
        List<ItemFrame> list = world.getEntitiesOfClass(ItemFrame.class, new AABB(pos), Objects::nonNull);
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Inject(method = "getInputSignal", at = @At(value = "RETURN"), cancellable = true)
    protected void isSolidBlockOrAir(Level world, BlockPos pos, BlockState state, CallbackInfoReturnable<Integer> cir) {
        if (CarpetExtraSettings.comparatorBetterItemFrames != ComparatorOptions.VANILLA) {
            int i = super.getInputSignal(world, pos, state);
            Direction direction = state.getValue(FACING);
            BlockPos blockPos = pos.relative(direction);
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.hasAnalogOutputSignal()) {
                i = blockState.getAnalogOutputSignal(world, blockPos, direction);
            } else if (i < 15 && blockState.isRedstoneConductor(world, blockPos)) {
                blockPos = blockPos.relative(direction);
                blockState = world.getBlockState(blockPos);
                ItemFrame itemFrameEntity = getAttachedItemFrameHorizontal(world, direction, blockPos);
                int j = Math.max(itemFrameEntity == null ? -2147483648 : itemFrameEntity.getAnalogOutput(), blockState.hasAnalogOutputSignal() ? blockState.getAnalogOutputSignal(world, blockPos, direction) : -2147483648);
=======
    private ItemFrameEntity getUnAttachedItemFrameHorizontal(World world, Direction facing, BlockPos pos) {
        // If item frame is sitting in front of the comparator, horizontally or on another block
        List<ItemFrameEntity> list = world.getEntitiesByClass(ItemFrameEntity.class, new Box(pos), Objects::nonNull);
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Inject(method = "getPower", at = @At(value = "RETURN"), cancellable = true)
    protected void isSolidBlockOrAir(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Integer> cir) {
        if (CarpetExtraSettings.comparatorBetterItemFrames != ComparatorOptions.VANILLA) {
            int i = super.getPower(world, pos, state);
            Direction direction = state.get(FACING);
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.hasComparatorOutput()) {
                i = blockState.getComparatorOutput(world, blockPos, direction);
            } else if (i < 15 && blockState.isSolidBlock(world, blockPos)) {
                blockPos = blockPos.offset(direction);
                blockState = world.getBlockState(blockPos);
                ItemFrameEntity itemFrameEntity = getAttachedItemFrameHorizontal(world, direction, blockPos);
                int j = Math.max(itemFrameEntity == null ? -2147483648 : itemFrameEntity.getComparatorPower(), blockState.hasComparatorOutput() ? blockState.getComparatorOutput(world, blockPos, direction) : -2147483648);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                if (j != -2147483648) {
                    i = j;
                }
            } else {
<<<<<<< HEAD
                ItemFrame itemFrame = getUnAttachedItemFrameHorizontal(world,direction,blockPos);
                if (itemFrame != null) {
                    i = itemFrame.getAnalogOutput();
=======
                ItemFrameEntity itemFrame = getUnAttachedItemFrameHorizontal(world,direction,blockPos);
                if (itemFrame != null) {
                    i = itemFrame.getComparatorPower();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                }
            }
            cir.setReturnValue(i);
        }
    }
}
