package carpetextra.mixins;

import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import net.minecraft.world.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.InventoryUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import carpet.CarpetSettings;

@Mixin(ItemInstance.class)
public interface ItemStackMixin {
    @Inject(method = "getMaxStackSize", at = @At("HEAD"), cancellable = true)
    private void allowEmptyShulkerBoxStacking(CallbackInfoReturnable<Integer> cir) {
        //noinspection ConstantValue
        if (((Object) this) instanceof ItemStack itemStack && CarpetExtraSettings.emptyShulkerBoxStackAlways && itemStack.getItem() instanceof BlockItem item) {
            ItemStack thisStack = (ItemStack) (Object) this;
            //noinspection UnreachableCode
            if (item.getBlock() instanceof ShulkerBoxBlock && !InventoryUtils.shulkerBoxHasItems(thisStack)) {
                cir.setReturnValue(CarpetSettings.shulkerBoxStackSize);
            }
        }
    }
}
