package carpetextra.utils;

<<<<<<< HEAD
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class VoidContainer extends AbstractContainerMenu
{
    public VoidContainer() { super(null, 1); }
    @Override
    public boolean stillValid(Player var1) { return false; }
    @Override
    public void slotsChanged(Container inventory_1) { }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
=======
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class VoidContainer extends ScreenHandler
{
    public VoidContainer() { super(null, 1); }
    @Override
    public boolean canUse(PlayerEntity var1) { return false; }
    @Override
    public void onContentChanged(Inventory inventory_1) { }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        throw new IllegalStateException("Didn't expect transferSlot to be called in fake container");
    }
}