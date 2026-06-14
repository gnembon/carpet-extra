package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import carpetextra.utils.PlaceBlockDispenserBehavior;
<<<<<<< HEAD
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;

@Mixin(targets = "net/minecraft/core/dispenser/DispenseItemBehavior$14")
public abstract class DispenserBehaviorCarvedPumpkinMixin extends OptionalDispenseItemBehavior
=======
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;

@Mixin(targets = "net/minecraft/block/dispenser/DispenserBehavior$6")
public abstract class DispenserBehaviorCarvedPumpkinMixin extends FallibleItemDispenserBehavior
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
{
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
    }*/
}
