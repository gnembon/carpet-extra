package carpetextra.helpers;

import carpet.utils.SpawnOverrides;
import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.EntityTypes;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.collection.WeightedPool;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.structure.StructureKeys;

public class CustomSpawnLists
{
    public static void addExtraSpawnRules()
    {
        SpawnOverrides.addOverride(() -> CarpetExtraSettings.straySpawningInIgloos, SpawnGroup.MONSTER, StructureKeys.IGLOO,
                StructureSpawns.BoundingBox.STRUCTURE, WeightedPool.of(new SpawnSettings.SpawnEntry(EntityTypes.STRAY, 1, 1)));
        SpawnOverrides.addOverride(() -> CarpetExtraSettings.creeperSpawningInJungleTemples, SpawnGroup.MONSTER, StructureKeys.JUNGLE_PYRAMID,
                StructureSpawns.BoundingBox.STRUCTURE, WeightedPool.of(new SpawnSettings.SpawnEntry(EntityTypes.CREEPER, 1, 1)));
    }
}
