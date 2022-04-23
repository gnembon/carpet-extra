package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.structure.StructureTypes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StrayEntity.class)
public abstract class StrayEntityMixin
{
    @Redirect(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;isSkyVisible(Lnet/minecraft/util/math/BlockPos;)Z"))
    private static boolean isSkylightOrIglooVisible(ServerWorldAccess serverWorldAccess, BlockPos pos)
    {
        return serverWorldAccess.isSkyVisible(pos) ||
                       (CarpetExtraSettings.straySpawningInIgloos && (((ServerWorld)serverWorldAccess).getStructureAccessor().getStructureAt(pos, StructureTypes.IGLOO.value()).hasChildren()));
    }
}
