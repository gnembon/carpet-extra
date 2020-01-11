package carpetextra.mixins;

import carpet.CarpetSettings;
import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.EntityCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import java.util.List;

@Mixin(FlatChunkGenerator.class)
public abstract class FlatChunkGenerator_strayMixin extends ChunkGenerator<FlatChunkGeneratorConfig>
{
    public FlatChunkGenerator_strayMixin(IWorld world, BiomeSource biomeSource, FlatChunkGeneratorConfig config)
    {
        super(world, biomeSource, config);
    }

    @Override
    public List<Biome.SpawnEntry> getEntitySpawnList(EntityCategory category, BlockPos pos) {
        if (CarpetSettings.flatWorldStructureSpawning && category == EntityCategory.MONSTER && CarpetExtraSettings.straySpawningInIgloos)
        {
            if (Feature.IGLOO.isApproximatelyInsideStructure(this.world, pos))
            {
                return Feature.IGLOO.getMonsterSpawns();
            }
        }
        return super.getEntitySpawnList(category, pos);
    }
}
