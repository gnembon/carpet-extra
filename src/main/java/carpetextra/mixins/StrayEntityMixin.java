package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StrayEntity.class)
public abstract class StrayEntityMixin
{
    @Redirect(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;isSkyVisible(Lnet/minecraft/util/math/BlockPos;)Z"))
    private static boolean isSkylightOrIglooVisible(ServerWorldAccess serverWorldAccess, BlockPos pos)
    {
        if (!CarpetExtraSettings.straySpawningInIgloos) {
            return serverWorldAccess.isSkyVisible(pos);
        }
        Structure structure = serverWorldAccess.getRegistryManager().get(Registry.STRUCTURE_KEY).get(StructureKeys.IGLOO);
        return serverWorldAccess.isSkyVisible(pos) ||
                       ((ServerWorld)serverWorldAccess).getStructureAccessor().getStructureAt(pos,structure).hasChildren();
    }
}
