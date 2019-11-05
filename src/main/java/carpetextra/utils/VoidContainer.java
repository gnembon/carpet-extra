package carpetextra.utils;

import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;

public class VoidContainer extends Container
{
    public VoidContainer() { super(null, 1); }
    @Override
    public boolean canUse(PlayerEntity var1) { return false; }
    @Override
    public void onContentChanged(Inventory inventory_1) { }
}