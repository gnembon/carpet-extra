package carpetextra.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
        NbtCompound nbt = stackShulkerBox.getNbt();

        if (nbt != null && nbt.contains("BlockEntityTag", Constants.NBT.COMPOUND_TAG))
        {
            NbtCompound tag = nbt.getCompound("BlockEntityTag");

            if (tag.contains("Items", Constants.NBT.LIST_TAG))
            {
                NbtList tagList = tag.getList("Items", Constants.NBT.COMPOUND_TAG);
                return tagList.size() > 0;
            }
        }

        return false;
    }
}
