package carpetextra.mixins;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import carpetextra.dispenser.CarpetExtraDispenserBehaviors;
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
    private void dispenseCustomBehavior(ServerWorld serverWorld, BlockPos pos, CallbackInfo ci, BlockPointerImpl blockPointerImpl, DispenserBlockEntity dispenserBlockEntity, int i, ItemStack itemStack) {
        // get custom behavior
        DispenserBehavior customBehavior = CarpetExtraDispenserBehaviors.getCustomDispenserBehavior(serverWorld, pos, blockPointerImpl, dispenserBlockEntity, itemStack, BEHAVIORS);

        // check if custom behavior exists
        if(customBehavior != null) {
            // run custom behavior
            dispenserBlockEntity.setStack(i, customBehavior.dispense(blockPointerImpl, itemStack));
            ci.cancel();
        }
    }
}
