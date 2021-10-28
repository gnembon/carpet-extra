package carpetextra.helpers;

import net.minecraft.entity.EntityType;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.biome.SpawnSettings;

public class CustomSpawnLists
{
    public static final Pool<SpawnSettings.SpawnEntry> IGLOO_SPAWNS = Pool.of(new SpawnSettings.SpawnEntry(EntityType.STRAY, 1, 1, 1));
    public static final Pool<SpawnSettings.SpawnEntry> JUNGLE_SPAWNS = Pool.of(new SpawnSettings.SpawnEntry(EntityType.CREEPER, 1, 1, 1));
}
