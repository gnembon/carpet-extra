package carpetextra.dispenser;

import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;

public abstract class DispenserBehaviorHelper extends FallibleItemDispenserBehavior {
    // adds new stack to dispenser inventory or dispenses if inventory is full
    protected ItemStack addOrDispense(BlockPointer pointer, ItemStack originalStack, ItemStack newStack) {
        // removes item from original stack
        originalStack.decrement(1);
        // check if original is now empty, if so return new stack in its place
        if (originalStack.isEmpty()) {
            return newStack;
        }
        // try to add new stack to inventory, if it can't be added, dispense
        if (!pointer.blockEntity().addToFirstFreeSlot(newStack).isEmpty()) {
            // if the newStack still contains items, then the addToFirstFreeSlot()
            // did not drain the stack fully -> there are items left to be dispensed
            super.dispenseSilently(pointer, newStack);
        }
        return originalStack;
    }
}
