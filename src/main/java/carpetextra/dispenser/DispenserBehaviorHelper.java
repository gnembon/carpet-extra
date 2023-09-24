package carpetextra.dispenser;

import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
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
        else if (((DispenserBlockEntity) pointer.blockEntity()).addToFirstFreeSlot(newStack) < 0) {
            super.dispenseSilently(pointer, newStack);
        }
        return originalStack;
    }
}
