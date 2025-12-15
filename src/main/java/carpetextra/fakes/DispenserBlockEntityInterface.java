package carpetextra.fakes;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface DispenserBlockEntityInterface
{
    public NonNullList<ItemStack> getInventory();
}
