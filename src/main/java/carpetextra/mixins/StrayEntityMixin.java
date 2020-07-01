package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.class_5425;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StrayEntity.class)
public abstract class StrayEntityMixin
{
    @Redirect(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_5425;isSkyVisible(Lnet/minecraft/util/math/BlockPos;)Z"))
    private static boolean isSkylightOrIglooVisible(class_5425 iWorld, BlockPos blockPos)
    {
        return iWorld.isSkyVisible(blockPos) ||
                       (CarpetExtraSettings.straySpawningInIgloos && (((ServerWorld)iWorld).getStructureAccessor().method_28388(blockPos, false, StructureFeature.IGLOO).hasChildren()));
    }
}
