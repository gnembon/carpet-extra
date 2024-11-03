package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.Vec3d;
import carpetextra.CarpetExtraSettings;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin
{
    @Redirect(method = "onPlayerInteractBlock",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/util/math/Vec3d;subtract(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"),
              require = 0)
    private Vec3d carpetextra_removeHitPosCheck(Vec3d hitVec, Vec3d blockCenter)
    {
        if (CarpetExtraSettings.accurateBlockPlacement || CarpetExtraSettings.accurateBlockPlacementLegacy)
        {
            return Vec3d.ZERO;
        }

        return hitVec.subtract(blockCenter);
    }
}
