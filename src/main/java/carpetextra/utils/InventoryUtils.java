package carpetextra.utils;

<<<<<<< HEAD
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
=======
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

public class InventoryUtils {
    /**
     * Checks if the given Shulker Box (or other storage item with the
     * same NBT data structure) currently contains any items.
     * @return true if the item's stored inventory has any items
     */
    public static boolean shulkerBoxHasItems(ItemStack stackShulkerBox) {
<<<<<<< HEAD
        ItemContainerContents containerComponent = stackShulkerBox.get(DataComponents.CONTAINER);
        return containerComponent != null && containerComponent.nonEmptyItems().iterator().hasNext();
=======
        ContainerComponent containerComponent = stackShulkerBox.get(DataComponentTypes.CONTAINER);
        return containerComponent != null && containerComponent.iterateNonEmpty().iterator().hasNext();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
