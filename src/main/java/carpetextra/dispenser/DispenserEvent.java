package carpetextra.dispenser;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import carpet.script.CarpetEventServer.Event;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import carpet.script.value.ValueConversions;

public class DispenserEvent extends Event {
    private static final DispenserEvent INSTANCE = new DispenserEvent();

    private DispenserEvent() {
        super("extra_dispenser_action", 5, true);
    }

    public static void call(BlockSource pointer, DispenseItemBehavior name, BlockPos pos, Value item, ItemStack resultItem) {
        INSTANCE.handler.call(() -> List.of(
                    new StringValue(getScarpetName(name.getClass().getSimpleName())),
                    ValueConversions.of(pos),
                    new StringValue(pointer.state().getValue(DispenserBlock.FACING).name().toLowerCase()),
                    item, // value directly because it needs to be a snapshot, stack is mutable
                    ValueConversions.of(resultItem, pointer.level().registryAccess())
                ),
                () ->  pointer.level().getServer().createCommandSourceStack().withLevel(pointer.level())
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
