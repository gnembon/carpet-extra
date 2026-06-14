package carpetextra.mixins;

import java.util.Map;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
=======

>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import carpet.script.value.Value;
import carpet.script.value.ValueConversions;
import carpetextra.dispenser.CarpetExtraDispenserBehaviors;
import carpetextra.dispenser.DispenserEvent;
import com.llamalad7.mixinextras.sugar.Local;

<<<<<<< HEAD
@Mixin(DispenserBlock.class)
public abstract class DispenserBlock_GetCustomBehaviorMixin {
    @Shadow @Final public static Map<Item, DispenseItemBehavior> DISPENSER_REGISTRY;

    @Inject(
            method = "dispenseFrom",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/DispenserBlock;getDispenseMethod(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/core/dispenser/DispenseItemBehavior;"
            ),
            cancellable = true
    )
    private void dispenseCustomBehavior(ServerLevel world, BlockState state, BlockPos pos, CallbackInfo ci, @Local DispenserBlockEntity dispenserEntity, @Local BlockSource pointer, @Local int slot, @Local ItemStack stack) {
        // get custom behavior
        DispenseItemBehavior behavior = CarpetExtraDispenserBehaviors.getCustomDispenserBehavior(world, pos, pointer, dispenserEntity, stack, DISPENSER_REGISTRY);
=======
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
            cancellable = true
    )
    private void dispenseCustomBehavior(ServerWorld world, BlockState state, BlockPos pos, CallbackInfo ci, @Local DispenserBlockEntity dispenserEntity, @Local BlockPointer pointer, @Local int slot, @Local ItemStack stack) {
        // get custom behavior
        DispenserBehavior behavior = CarpetExtraDispenserBehaviors.getCustomDispenserBehavior(world, pos, pointer, dispenserEntity, stack, BEHAVIORS);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

        // check if custom behavior exists
        if (behavior != null) {
            // run custom behavior
            Value previousStackSnapshot = null;
<<<<<<< HEAD
            if (DispenserEvent.needed()) previousStackSnapshot = ValueConversions.of(stack, world.registryAccess());

            ItemStack resultStack = behavior.dispense(pointer, stack);
            dispenserEntity.setItem(slot, resultStack);
=======
            if (DispenserEvent.needed()) previousStackSnapshot = ValueConversions.of(stack, world.getRegistryManager());

            ItemStack resultStack = behavior.dispense(pointer, stack);
            dispenserEntity.setStack(slot, resultStack);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

            DispenserEvent.call(pointer, behavior, pos, previousStackSnapshot, resultStack);

            ci.cancel();
        }
    }
}
