package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(OverworldChunkGenerator.class)
public abstract class OverworldChunkGeneratorMixin extends SurfaceChunkGenerator<OverworldChunkGeneratorConfig>
{
    public OverworldChunkGeneratorMixin(BiomeSource biomeSource, long l, OverworldChunkGeneratorConfig config, int verticalNoiseResolution, int horizontalNoiseResolution, int worldHeight, boolean useSimplexNoise)
    {
        super(biomeSource, l, config, verticalNoiseResolution, horizontalNoiseResolution, worldHeight, useSimplexNoise);
    }
    
    @Inject(
            method = "getEntitySpawnList",
            at = @At(value = "INVOKE", ordinal = 1, shift = At.Shift.BEFORE,
                    target = "Lnet/minecraft/world/gen/feature/StructureFeature;isApproximatelyInsideStructure(Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/util/math/BlockPos;)Z"),
            cancellable = true
    )
    private void onGetEntitySpawnList(Biome biome, StructureAccessor arg, SpawnGroup category, BlockPos pos, CallbackInfoReturnable<List<Biome.SpawnEntry>> cir)
    {
        if (CarpetExtraSettings.straySpawningInIgloos)
        {
            if (Feature.IGLOO.isApproximatelyInsideStructure(arg,  pos))
            {
                cir.setReturnValue(Feature.IGLOO.getMonsterSpawns());
            }
        }
        
        if (CarpetExtraSettings.creeperSpawningInJungleTemples)
        {
            if (Feature.JUNGLE_TEMPLE.isApproximatelyInsideStructure(arg, pos))
            {
                cir.setReturnValue(Feature.JUNGLE_TEMPLE.getMonsterSpawns());
            }
        }
    }
}
