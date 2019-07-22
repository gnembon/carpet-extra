package carpetextra.utils;

import net.minecraft.client.network.packet.GuiSlotUpdateS2CPacket;
import net.minecraft.container.CraftingTableContainer;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class AutoCraftingTableContainer extends CraftingTableContainer {
    private final CraftingTableBlockEntity blockEntity;
    private final PlayerEntity player;

    AutoCraftingTableContainer(int id, PlayerInventory playerInventory, CraftingTableBlockEntity blockEntity) {
        super(id, playerInventory);
        this.blockEntity = blockEntity;
        this.player = playerInventory.player;
        slotList.clear();
        this.addSlot(new OutputSlot(this.blockEntity));

        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 3; ++x) {
                this.addSlot(new Slot(this.blockEntity, x + y * 3 + 1, 30 + x * 18, 17 + y * 18));
            }
        }

        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for(int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
        }
    }

    @Override
    public void onContentChanged(Inventory inv) {
        if (this.player instanceof ServerPlayerEntity) {
            ServerPlayNetworkHandler netHandler = ((ServerPlayerEntity) this.player).networkHandler;
            netHandler.sendPacket(new GuiSlotUpdateS2CPacket(this.syncId, 0, this.blockEntity.getInvStack(0)));
        }
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int slot) {
        if (slot == 0) {
            ItemStack before = this.blockEntity.getInvStack(0).copy();
            ItemStack current = before.copy();
            if (!this.insertItem(current, 10, 46, true)) {
                return ItemStack.EMPTY;
            }
            this.blockEntity.takeInvStack(0, before.getCount() - current.getCount());
            return this.blockEntity.getInvStack(0);
        }
        return super.transferSlot(player, slot);
    }

    public void close(PlayerEntity player) {
        PlayerInventory playerInventory = player.inventory;
        if (!playerInventory.getCursorStack().isEmpty()) {
            player.dropItem(playerInventory.getCursorStack(), false);
            playerInventory.setCursorStack(ItemStack.EMPTY);
        }
        this.blockEntity.onContainerClose(this);
    }

    private class OutputSlot extends Slot {
        OutputSlot(Inventory inv) {
            super(inv, 0, 124, 35);
        }

        @Override
        public boolean canInsert(ItemStack itemStack_1) {
            return false;
        }

        @Override
        protected void onTake(int amount) {
            AutoCraftingTableContainer.this.blockEntity.takeInvStack(0, amount);
        }
    }
}
