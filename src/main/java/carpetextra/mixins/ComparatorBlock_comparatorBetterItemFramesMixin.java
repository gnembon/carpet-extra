package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.CarpetExtraSettings.ComparatorOptions;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

@Mixin(ComparatorBlock.class)
public abstract class ComparatorBlock_comparatorBetterItemFramesMixin extends AbstractRedstoneGateBlock {
    protected ComparatorBlock_comparatorBetterItemFramesMixin(Settings settings) {
        super(settings);
    }

    @Unique
    private ItemFrameEntity getAttachedItemFrameHorizontal(World world, Direction facing, BlockPos pos) {
        List<ItemFrameEntity> list;
        list = world.getEntitiesByClass(ItemFrameEntity.class, new Box(pos), (itemFrame) ->
                itemFrame != null && (CarpetExtraSettings.comparatorBetterItemFrames.ordinal() >= 2 || itemFrame.getHorizontalFacing() == facing));
        if (list.isEmpty() && CarpetExtraSettings.comparatorBetterItemFrames == ComparatorOptions.EXTENDED) {
            list = world.getEntitiesByClass(ItemFrameEntity.class, new Box(pos).expand(0.3), Objects::nonNull);
        }
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Unique
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
                i = blockState.getComparatorOutput(world, blockPos);
            } else if (i < 15 && blockState.isSolidBlock(world, blockPos)) {
                blockPos = blockPos.offset(direction);
                blockState = world.getBlockState(blockPos);
                ItemFrameEntity itemFrameEntity = getAttachedItemFrameHorizontal(world, direction, blockPos);
                int j = Math.max(itemFrameEntity == null ? -2147483648 : itemFrameEntity.getComparatorPower(), blockState.hasComparatorOutput() ? blockState.getComparatorOutput(world, blockPos) : -2147483648);
                if (j != -2147483648) {
                    i = j;
                }
            } else {
                ItemFrameEntity itemFrame = getUnAttachedItemFrameHorizontal(world,direction,blockPos);
                if (itemFrame != null) {
                    i = itemFrame.getComparatorPower();
                }
            }
            cir.setReturnValue(i);
        }
    }
}
