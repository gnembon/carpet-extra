package carpetextra.fakes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface DispenserBlockEntityInterface
{
    public DefaultedList<ItemStack> getInventory();
}
