package carpetextra.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;

public class WoodDelayMultipliers
{
    private static final Object2FloatMap<Block> defaults = new Object2FloatOpenHashMap<Block>() {{
        put(Blocks.BIRCH_PRESSURE_PLATE, 0.0f);
        put(Blocks.ACACIA_PRESSURE_PLATE, 0.2f);
        put(Blocks.JUNGLE_PRESSURE_PLATE, 0.5f);
        put(Blocks.OAK_PRESSURE_PLATE, 1.0f);
        put(Blocks.SPRUCE_PRESSURE_PLATE, 1.5f);
        put(Blocks.DARK_OAK_PRESSURE_PLATE, 2.0f);

        put(Blocks.BIRCH_BUTTON, 0.0f);
        put(Blocks.ACACIA_BUTTON, 0.2f);
        put(Blocks.JUNGLE_BUTTON, 0.5f);
        put(Blocks.OAK_BUTTON, 1.0f);
        put(Blocks.SPRUCE_BUTTON, 1.5f);
        put(Blocks.DARK_OAK_BUTTON, 2.0f);
        defaultReturnValue(1f);
    }};

    public static int getForDelay(Block block, int defaultDelay)
    {
        int modified = (int) (defaults.getFloat(block) *defaultDelay);
        if (modified == 0)
            return 1;
        return modified;
    }
}
