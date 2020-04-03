package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StrayEntity.class)
public abstract class StrayEntityMixin
{
    @Redirect(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/IWorld;isSkyVisible(Lnet/minecraft/util/math/BlockPos;)Z"))
    private static boolean isSkylightOrIglooVisible(IWorld iWorld, BlockPos blockPos)
    {
        return iWorld.isSkyVisible(blockPos) ||
                       (CarpetExtraSettings.straySpawningInIgloos && Feature.IGLOO.isApproximatelyInsideStructure(iWorld, ((ServerWorld)iWorld).method_27056(), blockPos));
    }
}
