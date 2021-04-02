package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.class_6012;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.IglooFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collections;
import java.util.List;

import static net.minecraft.class_6012.method_34988;

@Mixin(IglooFeature.class)
public abstract class IglooFeatureMixin extends StructureFeature<DefaultFeatureConfig>
{

    private static final class_6012<SpawnSettings.SpawnEntry> MONSTER_SPAWNS;

    public IglooFeatureMixin(Codec<DefaultFeatureConfig> codec)
    {
        super(codec);
    }

    @Override
    public class_6012<SpawnSettings.SpawnEntry> getMonsterSpawns()
    {
        if (CarpetExtraSettings.straySpawningInIgloos)
        {
            return MONSTER_SPAWNS;
        }
        return SpawnSettings.field_30982;
    }
    
    static
    {
        MONSTER_SPAWNS = class_6012.method_34989(new SpawnSettings.SpawnEntry(EntityType.STRAY, 1, 1, 1));
    }
}
