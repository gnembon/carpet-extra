package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.PlaceBlockDispenserBehavior;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin
{
    @Shadow @Final public static Map<Item, DispenseItemBehavior> DISPENSER_REGISTRY;

    // this is old code to keep dispenserPlacesBlocks working as is
    // new behaviors should go in CarpetExtraDispenserBehaviors class
    @Inject(method = "getDispenseMethod", at = @At("HEAD"), cancellable = true)
    private void getBehaviorForItem(Level world, ItemStack stack, CallbackInfoReturnable<DispenseItemBehavior> cir)
    {
        Item item = stack.getItem();
        if (CarpetExtraSettings.dispenserPlacesBlocks && !DISPENSER_REGISTRY.containsKey(item) && item instanceof BlockItem)
        {

            if (PlaceBlockDispenserBehavior.canPlace(((BlockItem) item).getBlock()))
            {
                cir.setReturnValue(PlaceBlockDispenserBehavior.getInstance());
                cir.cancel();
            }
        }
    }
}
