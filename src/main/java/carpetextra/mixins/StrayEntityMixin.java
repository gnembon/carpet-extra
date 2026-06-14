package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Stray.class)
public abstract class StrayEntityMixin
{
    @Redirect(method = "checkStraySpawnRules", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ServerLevelAccessor;canSeeSky(Lnet/minecraft/core/BlockPos;)Z"))
    private static boolean isSkylightOrIglooVisible(ServerLevelAccessor world, BlockPos pos)
    {
        if (!CarpetExtraSettings.straySpawningInIgloos) {
            return world.canSeeSky(pos);
        }
        Structure structure = world.registryAccess().lookupOrThrow(Registries.STRUCTURE).getValue(BuiltinStructures.IGLOO);
        return world.canSeeSky(pos) ||
                       ((ServerLevel)world).structureManager().getStructureAt(pos,structure).isValid();
    }
}
