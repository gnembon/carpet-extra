package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.CarpetExtraSettings.ComparatorOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFrame.class)
public abstract class ItemFrameEntity_comparatorBetterItemFramesMixin extends HangingEntity {
    public ItemFrameEntity_comparatorBetterItemFramesMixin(EntityType<? extends ItemFrame> type, Level world) {
        super(type, world);
    }

    @Inject(method = "setItem(Lnet/minecraft/world/item/ItemStack;Z)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/level/Level;updateNeighbourForOutputSignal(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V"), cancellable = true)
    @SuppressWarnings("resource")
    private void onStackChangeUpdateComparatorDownwards(ItemStack value, boolean update, CallbackInfo ci) {
        if (CarpetExtraSettings.comparatorBetterItemFrames == ComparatorOptions.EXTENDED) {
            this.level().updateNeighbourForOutputSignal(this.pos.relative(this.getDirection().getOpposite()), Blocks.AIR);
        }
    }

    @Inject(method = "setRotation(IZ)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/level/Level;updateNeighbourForOutputSignal(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V"), cancellable = true)
    @SuppressWarnings("resource")
    private void onRotationUpdateComparatorDownwards(int value, boolean bl, CallbackInfo ci) {
        if (CarpetExtraSettings.comparatorBetterItemFrames == ComparatorOptions.EXTENDED) {
            this.level().updateNeighbourForOutputSignal(this.pos.relative(this.getDirection().getOpposite()), Blocks.AIR);
        }
    }
}
