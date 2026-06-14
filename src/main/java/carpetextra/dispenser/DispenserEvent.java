package carpetextra.dispenser;

import java.util.List;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
=======

>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import carpet.script.CarpetEventServer.Event;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import carpet.script.value.ValueConversions;
<<<<<<< HEAD
=======
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

public class DispenserEvent extends Event {
    private static final DispenserEvent INSTANCE = new DispenserEvent();

    private DispenserEvent() {
        super("extra_dispenser_action", 5, true);
    }

<<<<<<< HEAD
    public static void call(BlockSource pointer, DispenseItemBehavior name, BlockPos pos, Value item, ItemStack resultItem) {
        INSTANCE.handler.call(() -> List.of(
                    new StringValue(getScarpetName(name.getClass().getSimpleName())),
                    ValueConversions.of(pos),
                    new StringValue(pointer.state().getValue(DispenserBlock.FACING).name().toLowerCase()),
                    item, // value directly because it needs to be a snapshot, stack is mutable
                    ValueConversions.of(resultItem, pointer.level().registryAccess())
                ),
                () ->  pointer.level().getServer().createCommandSourceStack().withLevel(pointer.level())
=======
    public static void call(BlockPointer pointer, DispenserBehavior name, BlockPos pos, Value item, ItemStack resultItem) {
        INSTANCE.handler.call(() -> List.of(
                    new StringValue(getScarpetName(name.getClass().getSimpleName())),
                    ValueConversions.of(pos),
                    new StringValue(pointer.state().get(DispenserBlock.FACING).name().toLowerCase()),
                    item, // value directly because it needs to be a snapshot, stack is mutable
                    ValueConversions.of(resultItem, pointer.world().getRegistryManager())
                ),
                () ->  pointer.world().getServer().getCommandSource().withWorld(pointer.world())
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        );
    }

    public static boolean needed() {
        return INSTANCE.isNeeded();
    }
    
    private static String getScarpetName(String className) {
        return className.substring(0, className.length() - "DispenserBehavior".length());
    }
    
    public static void init() {}
}
