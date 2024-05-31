package carpetextra.mixins;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
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
    @Inject(method = "shouldCancelStripAttempt", at = @At(value = "HEAD"), cancellable=true)
    private static void allowStripWithoutPlayer(ItemUsageContext context, CallbackInfoReturnable<Boolean> cir) {
        if (context.getPlayer() == null) {
            cir.setReturnValue(false);
        }
    }
}
