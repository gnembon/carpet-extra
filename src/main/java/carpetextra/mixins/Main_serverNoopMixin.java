package carpetextra.mixins;

import carpetextra.CarpetExtraServer;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class Main_serverNoopMixin
{
    @Inject(method = "main", at = @At("HEAD"))
    private static void onServerStarted(String[] args, CallbackInfo ci)
    {
        CarpetExtraServer.noop();
    }
}
