package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.IglooFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IglooFeature.class)
public abstract class IglooFeatureMixin extends StructureFeature<DefaultFeatureConfig>
{

    private static final Pool<SpawnSettings.SpawnEntry> MONSTER_SPAWNS;

    public IglooFeatureMixin(Codec<DefaultFeatureConfig> codec)
    {
        super(codec);
    }

    @Override
    public Pool<SpawnSettings.SpawnEntry> getMonsterSpawns()
    {
        if (CarpetExtraSettings.straySpawningInIgloos)
        {
            return MONSTER_SPAWNS;
        }
        return  SpawnSettings.field_30982;
    }
    
    static
    {
        MONSTER_SPAWNS = Pool.of(new SpawnSettings.SpawnEntry(EntityType.STRAY, 1, 1, 1));
    }
}
