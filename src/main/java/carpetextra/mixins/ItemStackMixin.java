package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.InventoryUtils;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemStackView;
import net.minecraft.item.SimpleItemStackView;
import carpet.CarpetSettings;

@Mixin(ItemStackView.class)
public interface ItemStackMixin {
    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    private void allowEmptyShulkerBoxStacking(CallbackInfoReturnable<Integer> cir) {
        ItemStack thisStack = switch ((ItemStackView)this) {
            case ItemStack is -> is;
            case SimpleItemStackView sisv -> sisv.toItemStack();
            default -> null; // for now, we don't handle arbitrary (modded) subclasses
        };
        if (thisStack != null && CarpetExtraSettings.emptyShulkerBoxStackAlways && thisStack.getItem() instanceof BlockItem item) {
            if (item.getBlock() instanceof ShulkerBoxBlock && !InventoryUtils.shulkerBoxHasItems(thisStack)) {
                cir.setReturnValue(CarpetSettings.shulkerBoxStackSize);
            }
        }
    }
}
