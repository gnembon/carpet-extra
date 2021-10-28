package carpetextra.mixins;

import carpetextra.helpers.CustomSpawnLists;
import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGenerator_customSpawnsMixin extends ChunkGenerator
{

    public NoiseChunkGenerator_customSpawnsMixin(BiomeSource biomeSource, StructuresConfig structuresConfig)
    {
        super(biomeSource, structuresConfig);
    }

    @Inject(method = "getEntitySpawnList", at = @At("HEAD"), cancellable = true)
    private void isInsidePyramid(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos, CallbackInfoReturnable<Pool<SpawnSettings.SpawnEntry>> cir)
    {
        if (group == SpawnGroup.MONSTER)
        {
            if (CarpetExtraSettings.straySpawningInIgloos)
            {
                if (accessor.getStructureAt(pos, StructureFeature.IGLOO).hasChildren())
                {
                    cir.setReturnValue(CustomSpawnLists.IGLOO_SPAWNS);
                    return;
                }
            }
            if (CarpetExtraSettings.creeperSpawningInJungleTemples)
            {
                if (accessor.getStructureAt(pos, StructureFeature.JUNGLE_PYRAMID).hasChildren())
                {
                    cir.setReturnValue(CustomSpawnLists.JUNGLE_SPAWNS);
                    return;
                }
            }
        }
    }
}
