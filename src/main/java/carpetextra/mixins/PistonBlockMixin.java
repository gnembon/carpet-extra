package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
=======
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

<<<<<<< HEAD
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
=======
@Mixin(PistonBlock.class)
public abstract class PistonBlockMixin
{
    @Inject(
            method = "tryMove",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 1,
                    target = "Lnet/minecraft/world/World;addSyncedBlockEvent(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;II)V")
    )
    private void onTryMove(World world, BlockPos pos, BlockState state, CallbackInfo ci)
    {
        if (CarpetExtraSettings.doubleRetraction)
        {
            world.setBlockState(pos, state.with(PistonBlock.EXTENDED, false), 2);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
    }
}
