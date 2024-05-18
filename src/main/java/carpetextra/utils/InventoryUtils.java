package carpetextra.utils;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

public class InventoryUtils
{
    /**
     * Checks if the given Shulker Box (or other storage item with the
     * same NBT data structure) currently contains any items.
     * @return true if the item's stored inventory has any items
     */
    public static boolean shulkerBoxHasItems(ItemStack stackShulkerBox)
    {
        NbtComponent nbtComponent = stackShulkerBox.get(DataComponentTypes.BLOCK_ENTITY_DATA);
        if (nbtComponent == null)
            return false;
        /* FIXME find a new way of doing this */
        NbtCompound nbt = nbtComponent.getNbt();

        if (nbt != null && nbt.contains("BlockEntityTag", NbtElement.COMPOUND_TYPE))
        {
            NbtCompound tag = nbt.getCompound("BlockEntityTag");

            if (tag.contains("Items", NbtElement.LIST_TYPE))
            {
                NbtList tagList = tag.getList("Items", NbtElement.COMPOUND_TYPE);
                return !tagList.isEmpty();
            }
        }

        return false;
    }
}
