package carpetextra.mixins;

<<<<<<< HEAD
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
=======
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public class AxeItem_allowStripMixin {
    /**
     * Fixes crash when invoking {@code #shouldCancelStripAttempt(ItemUsageContext)}
     * with {@code null} player in the context
     */
<<<<<<< HEAD
    @Inject(method = "playerHasBlockingItemUseIntent", at = @At(value = "HEAD"), cancellable=true)
    private static void allowStripWithoutPlayer(UseOnContext context, CallbackInfoReturnable<Boolean> cir) {
=======
    @Inject(method = "shouldCancelStripAttempt", at = @At(value = "HEAD"), cancellable=true)
    private static void allowStripWithoutPlayer(ItemUsageContext context, CallbackInfoReturnable<Boolean> cir) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        if (context.getPlayer() == null) {
            cir.setReturnValue(false);
        }
    }
}
