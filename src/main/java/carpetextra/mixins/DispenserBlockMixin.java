package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.PlaceBlockDispenserBehavior;
<<<<<<< HEAD
=======
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
<<<<<<< HEAD
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
=======
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin
{
<<<<<<< HEAD
    @Shadow @Final public static Map<Item, DispenseItemBehavior> DISPENSER_REGISTRY;

    // this is old code to keep dispenserPlacesBlocks working as is
    // new behaviors should go in CarpetExtraDispenserBehaviors class
    @Inject(method = "getDispenseMethod", at = @At("HEAD"), cancellable = true)
    private void getBehaviorForItem(Level world, ItemStack stack, CallbackInfoReturnable<DispenseItemBehavior> cir)
    {
        Item item = stack.getItem();
        if (CarpetExtraSettings.dispenserPlacesBlocks && !DISPENSER_REGISTRY.containsKey(item) && item instanceof BlockItem)
=======
    @Shadow @Final public static Map<Item, DispenserBehavior> BEHAVIORS;

    // this is old code to keep dispenserPlacesBlocks working as is
    // new behaviors should go in CarpetExtraDispenserBehaviors class
    @Inject(method = "getBehaviorForItem", at = @At("HEAD"), cancellable = true)
    private void getBehaviorForItem(World world, ItemStack stack, CallbackInfoReturnable<DispenserBehavior> cir)
    {
        Item item = stack.getItem();
        if (CarpetExtraSettings.dispenserPlacesBlocks && !BEHAVIORS.containsKey(item) && item instanceof BlockItem)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        {

            if (PlaceBlockDispenserBehavior.canPlace(((BlockItem) item).getBlock()))
            {
                cir.setReturnValue(PlaceBlockDispenserBehavior.getInstance());
                cir.cancel();
            }
        }
    }
}
