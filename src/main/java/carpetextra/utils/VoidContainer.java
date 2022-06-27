package carpetextra.utils;

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
    public ItemStack transferSlot(PlayerEntity player, int index) {
        throw new IllegalStateException("Didn't expect transferSlot to be called in fake container");
    }
}