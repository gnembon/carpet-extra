package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.BuddingAmethystBlock;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(BuddingAmethystBlock.class)
public class BuddingAmethystBlockMixin {
    @Inject(method = "getPistonBehavior", at = @At(value = "RETURN"), cancellable = true)
    private void injected(CallbackInfoReturnable<PistonBehavior> cir) {
        if (Objects.equals(CarpetExtraSettings.amethystPistonBehavior, "break")) {
            cir.setReturnValue(PistonBehavior.DESTROY);
        }
        else if (Objects.equals(CarpetExtraSettings.amethystPistonBehavior, "push")) {
            cir.setReturnValue(PistonBehavior.NORMAL);
        }
        else if (Objects.equals(CarpetExtraSettings.amethystPistonBehavior, "block")) {
            cir.setReturnValue(PistonBehavior.BLOCK);
        }
    }
}
