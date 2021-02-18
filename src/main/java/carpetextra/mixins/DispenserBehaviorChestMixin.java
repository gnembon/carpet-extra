package carpetextra.mixins;

import carpetextra.helpers.CarpetDispenserBehaviours;
import carpetextra.utils.PlaceBlockDispenserBehavior;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/block/dispenser/DispenserBehavior$5")
public abstract class DispenserBehaviorChestMixin
{
    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(
            method = "dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE,
                    target = "Lnet/minecraft/block/dispenser/FallibleItemDispenserBehavior;dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    private void handleBlockPlacing(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir)
    {
        if (stack.getItem() instanceof BlockItem && PlaceBlockDispenserBehavior.canPlace(((BlockItem) stack.getItem()).getBlock()))
            cir.setReturnValue(new CarpetDispenserBehaviours.MinecartDispenserBehaviour(AbstractMinecartEntity.Type.CHEST).dispenseSilently(pointer, stack));
    }
}
