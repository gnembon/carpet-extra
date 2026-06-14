package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.InventoryUtils;
<<<<<<< HEAD
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
=======
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import carpet.CarpetSettings;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();

<<<<<<< HEAD
    @Inject(method = "getMaxStackSize", at = @At("HEAD"), cancellable = true)
=======
    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
