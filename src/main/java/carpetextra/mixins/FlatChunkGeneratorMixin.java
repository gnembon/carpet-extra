package carpetextra.mixins;

import carpet.CarpetSettings;
import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = FlatChunkGenerator.class, priority = 999) // let carpet core create that method first
public abstract class FlatChunkGeneratorMixin extends ChunkGenerator
{
    public FlatChunkGeneratorMixin(BiomeSource biomeSource, ChunkGeneratorConfig chunkGeneratorConfig)
    {
        super(biomeSource, chunkGeneratorConfig);
    }

    /*
    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "getEntitySpawnList", at = @At("HEAD"), cancellable = true)
    private void onGetEntitySpawnList(Biome biome, StructureAccessor structureAccessor, SpawnGroup category, BlockPos pos, CallbackInfoReturnable<List<Biome.SpawnEntry>> cir)
    {
        if (CarpetSettings.flatWorldStructureSpawning && category == SpawnGroup.MONSTER)
        {
            if (CarpetExtraSettings.straySpawningInIgloos)
            {
                if (Feature.IGLOO.isApproximatelyInsideStructure(structureAccessor, pos))
                {
                    cir.setReturnValue(Feature.IGLOO.getMonsterSpawns());
                }
            }
            if (CarpetExtraSettings.creeperSpawningInJungleTemples)
            {
                if (Feature.JUNGLE_TEMPLE.isApproximatelyInsideStructure(structureAccessor, pos))
                {
                    cir.setReturnValue(Feature.JUNGLE_TEMPLE.getMonsterSpawns());
                }
            }
        }
    }*/

}
