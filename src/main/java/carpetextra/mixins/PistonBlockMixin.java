package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonBlock.class)
public abstract class PistonBlockMixin
{
    @Inject(
            method = "tryMove",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 1,
                    target = "Lnet/minecraft/world/World;addBlockAction(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;II)V")
    )
    private void onTryMove(World world, BlockPos pos, BlockState state, CallbackInfo ci)
    {
        if (CarpetExtraSettings.doubleRetraction)
        {
            world.setBlockState(pos, state.with(PistonBlock.EXTENDED, false), 2);
        }
    }
}
