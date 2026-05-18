package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public abstract class RedstoneWireBlockMixin_pistonRedirectsRedstone
{
    @Inject(
            method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void onConnectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir)
    {
        if ( CarpetExtraSettings.pistonRedirectsRedstone ) {
            if (state.getBlock() instanceof PistonBaseBlock)
            {
                cir.setReturnValue(dir != null && dir != state.getValue(PistonBaseBlock.FACING).getOpposite());
            }
        }
    }
}
