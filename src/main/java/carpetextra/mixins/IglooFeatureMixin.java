package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.AbstractTempleFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.IglooFeature;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Mixin(IglooFeature.class)
public abstract class IglooFeatureMixin extends AbstractTempleFeature<DefaultFeatureConfig>
{
    public IglooFeatureMixin(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory)
    {
        super(configFactory);
    }
    
    private static final List<Biome.SpawnEntry> MONSTER_SPAWNS;
    
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
