package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntity_comparatorBetterItemFramesMixin extends AbstractDecorationEntity {
    public ItemFrameEntity_comparatorBetterItemFramesMixin(EntityType<? extends ItemFrameEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Inject(method = "setHeldItemStack(Lnet/minecraft/item/ItemStack;Z)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/World;updateComparators(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V"), cancellable = true)
    private void onStackChangeUpdateComparatorDownwards(ItemStack value, boolean update, CallbackInfo ci) {
        if (CarpetExtraSettings.comparatorBetterItemFrames.id == 3) {
            this.world.updateComparators(this.attachmentPos.offset(this.getHorizontalFacing().getOpposite()), Blocks.AIR);
        }
    }

    @Inject(method = "setRotation(IZ)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/World;updateComparators(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V"), cancellable = true)
    private void onRotationUpdateComparatorDownwards(int value, boolean bl, CallbackInfo ci) {
        if (CarpetExtraSettings.comparatorBetterItemFrames.id == 3) {
            this.world.updateComparators(this.attachmentPos.offset(this.getHorizontalFacing().getOpposite()), Blocks.AIR);
        }
    }
}
