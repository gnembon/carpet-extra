package carpetextra.mixins;

import carpetextra.helpers.CarpetDispenserBehaviours;
import net.minecraft.block.dispenser.DispenserBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBehavior.class)
public interface DispenserBehaviourMixin
{
    @SuppressWarnings("PublicStaticMixinMember")
    @Inject(method = "registerDefaults", at = @At("TAIL"))
    static void onRegisterDefaults(CallbackInfo ci)
    {
        CarpetDispenserBehaviours.registerCarpetBehaviours();
    }
}
