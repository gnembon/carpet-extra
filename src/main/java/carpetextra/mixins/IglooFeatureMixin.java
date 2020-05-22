package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.IglooFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collections;
import java.util.List;

@Mixin(IglooFeature.class)
public abstract class IglooFeatureMixin extends StructureFeature<DefaultFeatureConfig>
{

    private static final List<Biome.SpawnEntry> MONSTER_SPAWNS;

    public IglooFeatureMixin(Codec<DefaultFeatureConfig> codec)
    {
        super(codec);
    }

    @Override
    public List<Biome.SpawnEntry> getMonsterSpawns()
    {
        if (CarpetExtraSettings.straySpawningInIgloos)
        {
            return MONSTER_SPAWNS;
        }
        return Collections.emptyList();
    }
    
    static
    {
        MONSTER_SPAWNS = Lists.newArrayList(new Biome.SpawnEntry(EntityType.STRAY, 1, 1, 1));
    }
}
