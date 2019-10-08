package carpetextra.helpers;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FeedableItems
{
    private static final Item[] FEEDABLEITEMS = new Item[] { Items.GOLDEN_APPLE, Items.GOLDEN_CARROT, Items.SWEET_BERRIES, Items.SEAGRASS,
            Items.DANDELION, Items.HAY_BLOCK, Items.WHEAT, Items.CARROT, Items.POTATO, Items.BEETROOT, Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS,
            Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.COD, Items.SALMON, Items.ROTTEN_FLESH, Items.PORKCHOP, Items.CHICKEN, Items.RABBIT,
            Items.BEEF, Items.MUTTON, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT };
    
    public static final Set<Item> ITEMS = new HashSet<Item>(Arrays.asList(FEEDABLEITEMS));
}
