package carpetextra.helpers;

import carpet.utils.SpawnOverrides;
import carpetextra.CarpetExtraSettings;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;

public class CustomSpawnLists
{
    public static void addExtraSpawnRules()
    {
        SpawnOverrides.addOverride(() -> CarpetExtraSettings.straySpawningInIgloos, MobCategory.MONSTER, BuiltinStructures.IGLOO,
                StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedList.of(new MobSpawnSettings.SpawnerData(EntityTypes.STRAY, 1, 1)));
        SpawnOverrides.addOverride(() -> CarpetExtraSettings.creeperSpawningInJungleTemples, MobCategory.MONSTER, BuiltinStructures.JUNGLE_TEMPLE,
                StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedList.of(new MobSpawnSettings.SpawnerData(EntityTypes.CREEPER, 1, 1)));
    }
}
