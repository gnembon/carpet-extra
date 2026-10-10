package carpetextra.mixins;

import net.minecraft.component.type.BlockTransformerComponent;
import net.minecraft.item.ItemUsageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockTransformerComponent.class)
public class BlockTransformerComponent_allowNullPlayerMixin {
    /**
     * Fixes crash when invoking the shield check with a {@code null} player in the context
     */
    @Inject(method = "isPreemptedByShield", at = @At("HEAD"), cancellable = true)
    private static void allowStripWithoutPlayer(ItemUsageContext context, CallbackInfoReturnable<Boolean> cir) {
        if (context.getPlayer() == null) {
            cir.setReturnValue(false);
        }
    }
}
