package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.CarpetExtraSettings.ComparatorOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

@Mixin(ComparatorBlock.class)
public abstract class ComparatorBlock_comparatorBetterItemFramesMixin extends DiodeBlock {
    protected ComparatorBlock_comparatorBetterItemFramesMixin(Properties settings) {
        super(settings);
    }

    @Unique
    private ItemFrame getAttachedItemFrameHorizontal(Level world, Direction facing, BlockPos pos) {
        List<ItemFrame> list;
        list = world.getEntitiesOfClass(ItemFrame.class, new AABB(pos), (itemFrame) ->
                itemFrame != null && (CarpetExtraSettings.comparatorBetterItemFrames.ordinal() >= 2 || itemFrame.getDirection() == facing));
        if (list.isEmpty() && CarpetExtraSettings.comparatorBetterItemFrames == ComparatorOptions.EXTENDED) {
            list = world.getEntitiesOfClass(ItemFrame.class, new AABB(pos).inflate(0.3), Objects::nonNull);
        }
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Unique
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
                if (j != -2147483648) {
                    i = j;
                }
            } else {
                ItemFrame itemFrame = getUnAttachedItemFrameHorizontal(world,direction,blockPos);
                if (itemFrame != null) {
                    i = itemFrame.getAnalogOutput();
                }
            }
            cir.setReturnValue(i);
        }
    }
}
