package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import carpetextra.CarpetExtraSettings;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.Vec3;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerPlayNetworkHandlerMixin
{
    @Redirect(method = "handleUseItemOn",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/phys/Vec3;subtract(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"),
              require = 0)
    private Vec3 carpetextra_removeHitPosCheck(Vec3 hitVec, Vec3 blockCenter)
    {
        if (CarpetExtraSettings.accurateBlockPlacement)
        {
            return Vec3.ZERO;
        }

        return hitVec.subtract(blockCenter);
    }
}
