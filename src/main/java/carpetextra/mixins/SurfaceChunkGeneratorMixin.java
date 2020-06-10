package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SurfaceChunkGenerator.class)
public abstract class SurfaceChunkGeneratorMixin extends ChunkGenerator
{

    public SurfaceChunkGeneratorMixin(BiomeSource biomeSource, StructuresConfig structuresConfig)
    {
        super(biomeSource, structuresConfig);
    }

    @Inject( method = "getEntitySpawnList", at = @At("HEAD"), cancellable = true
    )
    private void onGetEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos, CallbackInfoReturnable<List<Biome.SpawnEntry>> cir)
    {
        if (CarpetExtraSettings.straySpawningInIgloos && group == SpawnGroup.MONSTER)
        {
            if (accessor.method_28388(pos, false, StructureFeature.IGLOO).hasChildren())
            {
                cir.setReturnValue(StructureFeature.IGLOO.getMonsterSpawns());
            }
        }
        
        if (CarpetExtraSettings.creeperSpawningInJungleTemples)
        {
            if (accessor.method_28388(pos, false, StructureFeature.JUNGLE_PYRAMID).hasChildren())
            {
                cir.setReturnValue(StructureFeature.JUNGLE_PYRAMID.getMonsterSpawns());
            }
        }
    }
}
