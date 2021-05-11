package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(ComparatorBlock.class)
public abstract class ComparatorBlockMixin extends AbstractRedstoneGateBlock {
    protected ComparatorBlockMixin(Settings settings) {
        super(settings);
    }

    @Redirect(
        method = "onUse",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/player/PlayerEntity;abilities:Lnet/minecraft/entity/player/PlayerAbilities;"
        )
    )
    private PlayerAbilities hasPlayerAbilities(final PlayerEntity player) {
        // player will never be null in VANILLA
        if (player == null) return new PlayerAbilities();
        return player.abilities;
    }

    private ItemFrameEntity getAttachedItemFrameHorizontal(World world, Direction facing, BlockPos pos) {
        List<ItemFrameEntity> list = new ArrayList<>();
        list.addAll(world.getEntitiesByClass(ItemFrameEntity.class, new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1), (itemFrameEntity) -> {
            return itemFrameEntity != null && (CarpetExtraSettings.comparatorBetterItemFrames.id >= 2 || itemFrameEntity.getHorizontalFacing() == facing);
        }));
        if (list.size() == 0 && CarpetExtraSettings.comparatorBetterItemFrames.id >= 3) {
            list.addAll(world.getEntitiesByClass(ItemFrameEntity.class, new Box(pos.getX() - 0.3, pos.getY()-0.3, pos.getZ()-0.3, pos.getX() + 1.3, pos.getY() + 1.3, pos.getZ()+1.3), Objects::nonNull));
        }
        return list.size() >= 1 ? list.get(0) : null;
    }

    private ItemFrameEntity getUnAttachedItemFrameHorizontal(World world, Direction facing, BlockPos pos) {
        //If item frame is sitting in front of the comparator, horizontally or on another block
        List<ItemFrameEntity> list = world.getEntitiesByClass(ItemFrameEntity.class, new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1), Objects::nonNull);
        return list.size() == 1 ? list.get(0) : null;
    }

    @Inject(method = "getPower", at = @At(value = "INVOKE"), cancellable = true)
    protected void isSolidBlockOrAir(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Integer> cir) {
        if (CarpetExtraSettings.comparatorBetterItemFrames.id >= 1) {
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
                ItemFrameEntity itemFrameEntity = getUnAttachedItemFrameHorizontal(world,direction,blockPos);
                if (itemFrameEntity != null) {
                    i = itemFrameEntity.getComparatorPower();
                }
            }
            cir.setReturnValue(i);
        }
    }
}
