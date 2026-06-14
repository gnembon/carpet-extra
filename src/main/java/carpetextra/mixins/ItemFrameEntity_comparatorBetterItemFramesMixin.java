package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.CarpetExtraSettings.ComparatorOptions;
<<<<<<< HEAD
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
=======
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

<<<<<<< HEAD
@Mixin(ItemFrame.class)
public abstract class ItemFrameEntity_comparatorBetterItemFramesMixin extends HangingEntity {
    public ItemFrameEntity_comparatorBetterItemFramesMixin(EntityType<? extends ItemFrame> type, Level world) {
        super(type, world);
    }

    @Inject(method = "setItem(Lnet/minecraft/world/item/ItemStack;Z)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/level/Level;updateNeighbourForOutputSignal(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V"), cancellable = true)
    private void onStackChangeUpdateComparatorDownwards(ItemStack value, boolean update, CallbackInfo ci) {
        if (CarpetExtraSettings.comparatorBetterItemFrames == ComparatorOptions.EXTENDED) {
            this.level().updateNeighbourForOutputSignal(this.pos.relative(this.getDirection().getOpposite()), Blocks.AIR);
        }
    }

    @Inject(method = "setRotation(IZ)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/level/Level;updateNeighbourForOutputSignal(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;)V"), cancellable = true)
    private void onRotationUpdateComparatorDownwards(int value, boolean bl, CallbackInfo ci) {
        if (CarpetExtraSettings.comparatorBetterItemFrames == ComparatorOptions.EXTENDED) {
            this.level().updateNeighbourForOutputSignal(this.pos.relative(this.getDirection().getOpposite()), Blocks.AIR);
=======
@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntity_comparatorBetterItemFramesMixin extends AbstractDecorationEntity {
    public ItemFrameEntity_comparatorBetterItemFramesMixin(EntityType<? extends ItemFrameEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "setHeldItemStack(Lnet/minecraft/item/ItemStack;Z)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/World;updateComparators(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V"), cancellable = true)
    private void onStackChangeUpdateComparatorDownwards(ItemStack value, boolean update, CallbackInfo ci) {
        if (CarpetExtraSettings.comparatorBetterItemFrames == ComparatorOptions.EXTENDED) {
            this.getEntityWorld().updateComparators(this.attachedBlockPos.offset(this.getHorizontalFacing().getOpposite()), Blocks.AIR);
        }
    }

    @Inject(method = "setRotation(IZ)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/World;updateComparators(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V"), cancellable = true)
    private void onRotationUpdateComparatorDownwards(int value, boolean bl, CallbackInfo ci) {
        if (CarpetExtraSettings.comparatorBetterItemFrames == ComparatorOptions.EXTENDED) {
            this.getEntityWorld().updateComparators(this.attachedBlockPos.offset(this.getHorizontalFacing().getOpposite()), Blocks.AIR);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
    }
}
