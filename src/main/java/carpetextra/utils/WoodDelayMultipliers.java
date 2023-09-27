package carpetextra.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;

import static java.util.Map.entry;

public class WoodDelayMultipliers
{
    private static final Object2FloatMap<Block> defaults = new Object2FloatOpenHashMap<Block>(Map.ofEntries(
        entry(Blocks.BIRCH_PRESSURE_PLATE, 0.0f),
        entry(Blocks.ACACIA_PRESSURE_PLATE, 0.2f),
        entry(Blocks.JUNGLE_PRESSURE_PLATE, 0.5f),
        entry(Blocks.OAK_PRESSURE_PLATE, 1.0f),
        entry(Blocks.SPRUCE_PRESSURE_PLATE, 1.5f),
        entry(Blocks.DARK_OAK_PRESSURE_PLATE, 2.0f),

        entry(Blocks.BIRCH_BUTTON, 0.0f),
        entry(Blocks.ACACIA_BUTTON, 0.2f),
        entry(Blocks.JUNGLE_BUTTON, 0.5f),
        entry(Blocks.OAK_BUTTON, 1.0f),
        entry(Blocks.SPRUCE_BUTTON, 1.5f),
        entry(Blocks.DARK_OAK_BUTTON, 2.0f)
    ));
    static {
    	defaults.defaultReturnValue(1f);
    }

    public static int getForDelay(Block block, int defaultDelay)
    {
        int modified = (int) (defaults.getFloat(block) * defaultDelay);
        if (modified == 0)
            return 1;
        return modified;
    }
}
