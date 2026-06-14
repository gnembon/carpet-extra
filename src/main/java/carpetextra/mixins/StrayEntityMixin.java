package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
=======
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;

>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

<<<<<<< HEAD
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
=======
@Mixin(StrayEntity.class)
public abstract class StrayEntityMixin
{
    @Redirect(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;isSkyVisible(Lnet/minecraft/util/math/BlockPos;)Z"))
    private static boolean isSkylightOrIglooVisible(ServerWorldAccess world, BlockPos pos)
    {
        if (!CarpetExtraSettings.straySpawningInIgloos) {
            return world.isSkyVisible(pos);
        }
        Structure structure = world.getRegistryManager().getOrThrow(RegistryKeys.STRUCTURE).get(StructureKeys.IGLOO);
        return world.isSkyVisible(pos) ||
                       ((ServerWorld)world).getStructureAccessor().getStructureAt(pos,structure).hasChildren();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
