package carpetextra.mixins;

import carpet.utils.Messenger;
import carpetextra.CarpetExtraSettings;
import carpetextra.helpers.ThrowableSuppression;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.crash.CrashException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServer_updateSuppressionCrashFixMixin {
    @Redirect(method = "tickWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;tick(Ljava/util/function/BooleanSupplier;)V"))
    private void ficUpdateSuppressionCrashTick(ServerWorld serverWorld, BooleanSupplier shouldKeepTicking){
        if (!CarpetExtraSettings.updateSuppressionCrashFix) {
            serverWorld.tick(shouldKeepTicking);
            return;
        }
        try {
            serverWorld.tick(shouldKeepTicking);
        } catch (CrashException e) {
            if (!(e.getCause() instanceof ThrowableSuppression)) throw e;
            logUpdateSuppression("world tick");
        } catch (ThrowableSuppression ignored) {
            logUpdateSuppression("world tick");
        }
    }


    private void logUpdateSuppression(String phase) {
        Messenger.print_server_message((MinecraftServer) (Object) this, "You just caused a server crash in " + phase + ".");
    }
}
