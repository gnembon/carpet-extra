package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import carpetextra.utils.PlaceBlockDispenserBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;

//@Mixin(targets = {
//        "net/minecraft/core/dispenser/DispenseItemBehavior$14",
        /* wither skull */
        //"net/minecraft/block/dispenser/DispenserBehavior$7"
//})
public abstract class DispenserBehaviorMobHeadMixin extends OptionalDispenseItemBehavior {
    /* difficult to maintain
    @Inject(
            method = "dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
            at = @At("RETURN"),
            cancellable = true
    )
    private void handleBlockPlacing(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir)
    {
        if (!this.isSuccess() && stack.getItem() instanceof BlockItem && PlaceBlockDispenserBehavior.canPlace(((BlockItem) stack.getItem()).getBlock()))
        {
            this.setSuccess(true);
            cir.setReturnValue(PlaceBlockDispenserBehavior.getInstance().dispenseSilently(pointer, stack));
        }
    }
    */
}
