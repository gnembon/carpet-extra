package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.InventoryUtils;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import carpet.CarpetSettings;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();

    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    private void allowEmptyShulkerBoxStacking(CallbackInfoReturnable<Integer> cir) {
        if (CarpetExtraSettings.emptyShulkerBoxStackAlways && this.getItem() instanceof BlockItem item) {
            ItemStack thisStack = (ItemStack) (Object) this;
            //noinspection UnreachableCode
            if (item.getBlock() instanceof ShulkerBoxBlock && !InventoryUtils.shulkerBoxHasItems(thisStack)) {
                cir.setReturnValue(CarpetSettings.shulkerBoxStackSize);
            }
        }
    }
}
