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
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlock_GetCustomBehaviorMixin {
    @Shadow @Final public static Map<Item, DispenserBehavior> BEHAVIORS;

    @Inject(
        method = "dispense",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/DispenserBlock;getBehaviorForItem(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/block/dispenser/DispenserBehavior;"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    private void dispenseCustomBehavior(ServerWorld world, BlockState state, BlockPos pos, CallbackInfo ci, DispenserBlockEntity dispenserEntity, BlockPointer pointer, int slot, ItemStack stack) {
        // get custom behavior
        DispenserBehavior behavior = CarpetExtraDispenserBehaviors.getCustomDispenserBehavior(world, pos, pointer, dispenserEntity, stack, BEHAVIORS);

        // check if custom behavior exists
        if (behavior != null) {
            // run custom behavior
            Value previousStackSnapshot = null;
            if (DispenserEvent.needed()) previousStackSnapshot = ValueConversions.of(stack, world.getRegistryManager());

            ItemStack resultStack = behavior.dispense(pointer, stack);
            dispenserEntity.setStack(slot, resultStack);

            DispenserEvent.call(pointer, behavior, pos, previousStackSnapshot, resultStack);

            ci.cancel();
        }
    }
}
