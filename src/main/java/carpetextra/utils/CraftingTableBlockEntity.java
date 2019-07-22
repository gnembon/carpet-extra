package carpetextra.utils;

import carpetextra.mixins.CraftingInventoryMixin;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.Direction;
//import quickcarpet.utils.CarpetRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CraftingTableBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider {
    private static final int[] OUTPUT_SLOTS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static final int[] INPUT_SLOTS = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    public DefaultedList<ItemStack> inventory;
    public ItemStack output = ItemStack.EMPTY;
    private List<AutoCraftingTableContainer> openContainers = new ArrayList<>();

    public CraftingTableBlockEntity() {  //this(BlockEntityType.BARREL);
        this(ExtrasRegistry.CRAFTING_TABLE_BLOCK_ENTITY_TYPE);
    }

    private CraftingInventory craftingInventory = new CraftingInventory(null, 3, 3);

    private CraftingTableBlockEntity(BlockEntityType<?> type) {
        super(type);
        this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
        ((CraftingInventoryMixin) craftingInventory).setInventory(this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, inventory);
        tag.put("Output", output.toTag(new CompoundTag()));
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        Inventories.fromTag(tag, inventory);
        this.output = ItemStack.fromTag(tag.getCompound("Output"));
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.crafting");
    }

    @Override
    protected Container createContainer(int id, PlayerInventory playerInventory) {
        AutoCraftingTableContainer container = new AutoCraftingTableContainer(id, playerInventory, this);
        this.openContainers.add(container);
        return container;
    }

    @Override
    public int[] getInvAvailableSlots(Direction dir) {
        if (dir == Direction.DOWN && (!output.isEmpty() || getCurrentRecipe().isPresent())) return OUTPUT_SLOTS;
        return INPUT_SLOTS;
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) {
        return slot > 0 && getInvStack(slot).isEmpty();
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) {
        if (slot == 0) return !output.isEmpty() || getCurrentRecipe().isPresent();
        return true;
    }

    @Override
    public boolean isValidInvStack(int slot, ItemStack stack) {
        return slot != 0;
    }

    @Override
    public int getInvSize() {
        return 10;
    }

    @Override
    public boolean isInvEmpty() {
        for (ItemStack stack : this.inventory) {
            if (!stack.isEmpty()) return false;
        }
        return output.isEmpty();
    }

    @Override
    public ItemStack getInvStack(int slot) {
        if (slot > 0) return this.inventory.get(slot - 1);
        if (!output.isEmpty()) return output;
        Optional<CraftingRecipe> recipe = getCurrentRecipe();
        return recipe.map(craftingRecipe -> craftingRecipe.craft(craftingInventory)).orElse(ItemStack.EMPTY);
    }

    @Override
    public ItemStack takeInvStack(int slot, int amount) {
        if (slot == 0) {
            if (output.isEmpty()) {
                output = craft();
            }
            return output.split(amount);
        }
        return Inventories.splitStack(this.inventory, slot - 1, amount);
    }

    @Override
    public ItemStack removeInvStack(int slot) {
        if (slot == 0) {
            ItemStack output = this.output;
            this.output = ItemStack.EMPTY;
            return output;
        }
        return Inventories.removeStack(this.inventory, slot - 1);
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) {
        if (slot == 0) {
            output = stack;
            return;
        }
        inventory.set(slot - 1, stack);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        for (AutoCraftingTableContainer c : openContainers) c.onContentChanged(this);
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity var1) {
        return true;
    }

    @Override
    public void provideRecipeInputs(RecipeFinder finder) {
        for (ItemStack stack : this.inventory) {
            finder.addItem(stack);
        }
    }

    @Override
    public void setLastRecipe(Recipe<?> var1) {

    }

    @Override
    public Recipe<?> getLastRecipe() {
        return null;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    private Optional<CraftingRecipe> getCurrentRecipe() {
        if (this.world == null) return Optional.empty();
        return this.world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
    }

    private ItemStack craft() {
        if (this.world == null) return ItemStack.EMPTY;
        Optional<CraftingRecipe> optionalRecipe = getCurrentRecipe();
        if (!optionalRecipe.isPresent()) return ItemStack.EMPTY;
        CraftingRecipe recipe = optionalRecipe.get();
        ItemStack result = recipe.craft(craftingInventory);
        DefaultedList<ItemStack> remaining = world.getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, craftingInventory, world);
        for (int i = 0; i < 9; i++) {
            ItemStack current = inventory.get(i);
            ItemStack remainingStack = remaining.get(i);
            if (!current.isEmpty()) {
                current.decrement(1);
            }
            if (!remainingStack.isEmpty()) {
                if (current.isEmpty()) {
                    inventory.set(i, remainingStack);
                } else if (ItemStack.areItemsEqualIgnoreDamage(current, remainingStack) && ItemStack.areTagsEqual(current, remainingStack)) {
                    current.increment(remainingStack.getCount());
                } else {
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), remainingStack);
                }
            }
        }
        markDirty();
        return result;
    }

    public void onContainerClose(AutoCraftingTableContainer container) {
        this.openContainers.remove(container);
    }
}
