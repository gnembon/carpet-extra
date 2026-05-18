package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonBaseBlock.class)
public abstract class PistonBlockMixin
{
    @Inject(
            method = "checkIfExtend",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 1,
                    target = "Lnet/minecraft/world/level/Level;blockEvent(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;II)V")
    )
    private void onTryMove(Level world, BlockPos pos, BlockState state, CallbackInfo ci)
    {
        if (CarpetExtraSettings.doubleRetraction)
        {
            world.setBlock(pos, state.setValue(PistonBaseBlock.EXTENDED, false), 2);
        }
    }
}
