package carpetextra.utils;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class InventoryUtils {
    /**
     * Checks if the given Shulker Box (or other storage item with the
     * same NBT data structure) currently contains any items.
     * @return true if the item's stored inventory has any items
     */
    public static boolean shulkerBoxHasItems(ItemStack stackShulkerBox) {
        ItemContainerContents containerComponent = stackShulkerBox.get(DataComponents.CONTAINER);
        return containerComponent != null && !containerComponent.nonEmptyItems().iterator().hasNext();
    }
}
