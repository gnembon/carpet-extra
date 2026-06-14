package carpetextra.helpers;

import carpet.utils.SpawnOverrides;
import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
=======
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.structure.StructureKeys;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

public class CustomSpawnLists
{
    public static void addExtraSpawnRules()
    {
<<<<<<< HEAD
        SpawnOverrides.addOverride(() -> CarpetExtraSettings.straySpawningInIgloos, MobCategory.MONSTER, BuiltinStructures.IGLOO,
                StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedList.of(new MobSpawnSettings.SpawnerData(EntityType.STRAY, 1, 1)));
        SpawnOverrides.addOverride(() -> CarpetExtraSettings.creeperSpawningInJungleTemples, MobCategory.MONSTER, BuiltinStructures.JUNGLE_TEMPLE,
                StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedList.of(new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 1, 1)));
=======
        SpawnOverrides.addOverride(() -> CarpetExtraSettings.straySpawningInIgloos, SpawnGroup.MONSTER, StructureKeys.IGLOO,
                StructureSpawns.BoundingBox.STRUCTURE, Pool.of(new SpawnSettings.SpawnEntry(EntityType.STRAY, 1, 1)));
        SpawnOverrides.addOverride(() -> CarpetExtraSettings.creeperSpawningInJungleTemples, SpawnGroup.MONSTER, StructureKeys.JUNGLE_PYRAMID,
                StructureSpawns.BoundingBox.STRUCTURE, Pool.of(new SpawnSettings.SpawnEntry(EntityType.CREEPER, 1, 1)));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
