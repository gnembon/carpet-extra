package carpetextra.dispenser;

import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;

public abstract class DispenserBehaviorHelper extends OptionalDispenseItemBehavior {
    // adds new stack to dispenser inventory or dispenses if inventory is full
    protected ItemStack addOrDispense(BlockSource pointer, ItemStack originalStack, ItemStack newStack) {
        // removes item from original stack
        originalStack.shrink(1);
        // check if original is now empty, if so return new stack in its place
        if (originalStack.isEmpty()) {
            return newStack;
        }
        // try to add new stack to inventory, if it can't be added, dispense
        if (!pointer.blockEntity().insertItem(newStack).isEmpty()) {
            // if the newStack still contains items, then the addToFirstFreeSlot()
            // did not drain the stack fully -> there are items left to be dispensed
            super.execute(pointer, newStack);
        }
        return originalStack;
    }
}
