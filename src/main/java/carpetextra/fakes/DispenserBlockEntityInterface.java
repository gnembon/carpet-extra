package carpetextra.fakes;

<<<<<<< HEAD
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface DispenserBlockEntityInterface
{
    public NonNullList<ItemStack> getInventory();
=======
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface DispenserBlockEntityInterface
{
    public DefaultedList<ItemStack> getInventory();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
}
