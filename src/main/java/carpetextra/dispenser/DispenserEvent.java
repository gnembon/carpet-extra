package carpetextra.dispenser;

import java.util.List;

import carpet.script.CarpetEventServer.Event;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import carpet.script.value.ValueConversions;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;

public class DispenserEvent extends Event {
    private static final DispenserEvent INSTANCE = new DispenserEvent();

    private DispenserEvent() {
        super("extra_dispenser_action", 5, true);
    }

    public static void call(BlockPointer pointer, DispenserBehavior name, BlockPos pos, Value item, ItemStack resultItem) {
        INSTANCE.handler.call(() -> List.of(
                    new StringValue(getScarpetName(name.getClass().getSimpleName())),
                    ValueConversions.of(pos),
                    new StringValue(pointer.getBlockState().get(DispenserBlock.FACING).name().toLowerCase()),
                    item, // value directly because it needs to be a snapshot, stack is mutable
                    ValueConversions.of(resultItem, pointer.getWorld().getRegistryManager())
                ),
                () ->  pointer.getWorld().getServer().getCommandSource().withWorld(pointer.getWorld())
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
