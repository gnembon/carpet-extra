package carpetextra.utils;

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
        throw new IllegalStateException("Didn't expect transferSlot to be called in fake container");
    }
}