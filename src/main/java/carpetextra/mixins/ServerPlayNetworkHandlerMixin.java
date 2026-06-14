package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
<<<<<<< HEAD
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
=======
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
        if (CarpetExtraSettings.accurateBlockPlacement)
        {
            return Vec3d.ZERO;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }

        return hitVec.subtract(blockCenter);
    }
}
