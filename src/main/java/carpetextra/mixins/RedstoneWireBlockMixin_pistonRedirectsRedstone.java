package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
=======
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.Direction;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

<<<<<<< HEAD
@Mixin(RedStoneWireBlock.class)
public abstract class RedstoneWireBlockMixin_pistonRedirectsRedstone
{
    @Inject(
            method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z",
=======
@Mixin(RedstoneWireBlock.class)
public abstract class RedstoneWireBlockMixin_pistonRedirectsRedstone
{
    @Inject(
            method = "Lnet/minecraft/block/RedstoneWireBlock;connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z",
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void onConnectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir)
    {
        if ( CarpetExtraSettings.pistonRedirectsRedstone ) {
<<<<<<< HEAD
            if (state.getBlock() instanceof PistonBaseBlock)
            {
                cir.setReturnValue(dir != null && dir != state.getValue(PistonBaseBlock.FACING).getOpposite());
=======
            if (state.getBlock() instanceof PistonBlock)
            {
                cir.setReturnValue(dir != null && dir != state.get(PistonBlock.FACING).getOpposite());
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            }
        }
    }
}
