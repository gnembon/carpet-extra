package carpetextra.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;

public class VoidContainer extends ScreenHandler
{
    public VoidContainer() { super(null, 1); }
    @Override
    public boolean canUse(PlayerEntity var1) { return false; }
    @Override
    public void onContentChanged(Inventory inventory_1) { }
}