package carpetextra.helpers;

import carpet.utils.SpawnOverrides;
import carpetextra.CarpetExtraSettings;
import net.minecraft.class_7061;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;

public class CustomSpawnLists
{
    public static void addExtraSpawnRules()
    {
        SpawnOverrides.addOverride(() -> CarpetExtraSettings.straySpawningInIgloos, SpawnGroup.MONSTER, ConfiguredStructureFeatures.field_26298,
                class_7061.class_7062.STRUCTURE, Pool.of(new SpawnSettings.SpawnEntry(EntityType.STRAY, 1, 1, 1)));
        SpawnOverrides.addOverride(() -> CarpetExtraSettings.creeperSpawningInJungleTemples, SpawnGroup.MONSTER, ConfiguredStructureFeatures.field_26296,
                class_7061.class_7062.STRUCTURE, Pool.of(new SpawnSettings.SpawnEntry(EntityType.CREEPER, 1, 1, 1)));
    }
}
