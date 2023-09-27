package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
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
    private static boolean isSkylightOrIglooVisible(ServerWorldAccess world, BlockPos pos)
    {
        if (!CarpetExtraSettings.straySpawningInIgloos) {
            return world.isSkyVisible(pos);
        }
        Structure structure = world.getRegistryManager().get(RegistryKeys.STRUCTURE).get(StructureKeys.IGLOO);
        return world.isSkyVisible(pos) ||
                       ((ServerWorld)world).getStructureAccessor().getStructureAt(pos,structure).hasChildren();
    }
}
