package carpetextra.mixins;

import carpetextra.utils.PlaceBlockDispenserBehavior;
<<<<<<< HEAD
=======
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

<<<<<<< HEAD
@Mixin(targets = "net/minecraft/core/dispenser/DispenseItemBehavior$3")
=======
@Mixin(targets = "net/minecraft/block/dispenser/DispenserBehavior$9")
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
public abstract class DispenserBehaviorChestMixin
{
    /* difficult to maintain
    @Inject(
            method = "dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
            at = @At(value = "INVOKE",
                    shift = At.Shift.BEFORE,
                    target = "Lnet/minecraft/block/dispenser/FallibleItemDispenserBehavior;dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    private void handleBlockPlacing(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir)
    {
        if (stack.getItem() instanceof BlockItem && PlaceBlockDispenserBehavior.canPlace(((BlockItem) stack.getItem()).getBlock()))
            cir.setReturnValue(PlaceBlockDispenserBehavior.getInstance().dispenseSilently(pointer, stack));
    }
    */
}
