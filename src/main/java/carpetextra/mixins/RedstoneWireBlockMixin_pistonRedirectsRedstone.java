package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireBlock.class)
public abstract class RedstoneWireBlockMixin_pistonRedirectsRedstone
{
    @Inject(
            method = "Lnet/minecraft/block/RedstoneWireBlock;connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void onConnectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir)
    {
        if (state.isOf(Blocks.PISTON) && CarpetExtraSettings.pistonRedirectsRedstone) {
            cir.setReturnValue(dir != state.get(PistonBlock.FACING).getOpposite());
            cir.cancel();
        }
    }
}