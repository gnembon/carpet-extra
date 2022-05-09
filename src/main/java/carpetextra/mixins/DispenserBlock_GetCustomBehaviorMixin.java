package carpetextra.mixins;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import carpet.script.value.Value;
import carpet.script.value.ValueConversions;
import carpetextra.dispenser.CarpetExtraDispenserBehaviors;
import carpetextra.dispenser.DispenserEvent;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlock_GetCustomBehaviorMixin {
    @Shadow @Final private static Map<Item, DispenserBehavior> BEHAVIORS;

    @Inject(
        method = "dispense",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/DispenserBlock;getBehaviorForItem(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/block/dispenser/DispenserBehavior;"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    private void dispenseCustomBehavior(ServerWorld serverWorld, BlockPos pos, CallbackInfo ci, BlockPointerImpl blockPointer, DispenserBlockEntity dispenserBlockEntity, int i, ItemStack itemStack) {
        // get custom behavior
        DispenserBehavior behavior = CarpetExtraDispenserBehaviors.getCustomDispenserBehavior(serverWorld, pos, blockPointer, dispenserBlockEntity, itemStack, BEHAVIORS);

        // check if custom behavior exists
        if (behavior != null) {
            // run custom behavior
            Value previousStackSnapshot = null;
            if (DispenserEvent.needed()) previousStackSnapshot = ValueConversions.of(itemStack);

            ItemStack resultStack = behavior.dispense(blockPointer, itemStack);
            dispenserBlockEntity.setStack(i, resultStack);

            DispenserEvent.call(blockPointer, behavior, pos, previousStackSnapshot, resultStack);

            ci.cancel();
        }
    }
}
