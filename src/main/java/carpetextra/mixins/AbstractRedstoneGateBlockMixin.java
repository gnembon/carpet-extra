package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.DiodeBlock;
=======
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.util.math.Direction;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

<<<<<<< HEAD
@Mixin(DiodeBlock.class)
public abstract class AbstractRedstoneGateBlockMixin
{
    @Redirect(
            method = "shouldPrioritize",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/core/Direction;getOpposite()Lnet/minecraft/core/Direction;")
=======
@Mixin(AbstractRedstoneGateBlock.class)
public abstract class AbstractRedstoneGateBlockMixin
{
    @Redirect(
            method = "isTargetNotAligned",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/math/Direction;getOpposite()Lnet/minecraft/util/math/Direction;")
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    )
    private Direction onIsTargetNotAligned(Direction direction)
    {
        if (CarpetExtraSettings.repeaterPriorityFix)
            return direction;
        else
            return direction.getOpposite();
    }
}
