package carpetextra.utils;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;

public class InventoryUtils {
    /**
     * Checks if the given Shulker Box (or other storage item with the
     * same NBT data structure) currently contains any items.
     * @return true if the item's stored inventory has any items
     */
    public static boolean shulkerBoxHasItems(ItemStack stackShulkerBox) {
        ContainerComponent containerComponent = stackShulkerBox.get(DataComponentTypes.CONTAINER);
        return containerComponent != null && !containerComponent.iterateNonEmpty().iterator().hasNext();
    }
}
