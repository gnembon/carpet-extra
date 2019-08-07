package carpetextra.mixins;

import carpetextra.helpers.CarpetDispenserBehaviours;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBehavior.class)
public interface DispenserBehaviourMixin
{
    @SuppressWarnings("PublicStaticMixinMember")
    @Inject(method = "registerDefaults", at = @At("HEAD"))
    static void addBlockRotator(CallbackInfo ci)
    {
        DispenserBlock.registerBehavior(Items.GLASS_BOTTLE, new CarpetDispenserBehaviours.WaterBottleDispenserBehaviour());
    }
}
