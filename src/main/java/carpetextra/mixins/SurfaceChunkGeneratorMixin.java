package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.SpawnGroup;
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

import java.util.List;

@Mixin(NoiseChunkGenerator.class)
public abstract class SurfaceChunkGeneratorMixin extends ChunkGenerator
{

    public SurfaceChunkGeneratorMixin(BiomeSource biomeSource, StructuresConfig structuresConfig)
    {
        super(biomeSource, structuresConfig);
    }

    @Inject( method = "getEntitySpawnList", at = @At("HEAD"), cancellable = true
    )
    private void onGetEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos, CallbackInfoReturnable<List<SpawnSettings.SpawnEntry>> cir)
    {
        if (CarpetExtraSettings.straySpawningInIgloos && group == SpawnGroup.MONSTER)
        {
            if (accessor.getStructureAt(pos, false, StructureFeature.IGLOO).hasChildren())
            {
                cir.setReturnValue(StructureFeature.IGLOO.getMonsterSpawns().method_34994());
            }
        }
        
        if (CarpetExtraSettings.creeperSpawningInJungleTemples)
        {
            if (accessor.getStructureAt(pos, false, StructureFeature.JUNGLE_PYRAMID).hasChildren())
            {
                cir.setReturnValue(StructureFeature.JUNGLE_PYRAMID.getMonsterSpawns().method_34994());
            }
        }
    }
}
