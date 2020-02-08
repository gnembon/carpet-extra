package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractRedstoneGateBlock.class)
public abstract class AbstractRedstoneGateBlockMixin
{
    @Redirect(
            method = "isTargetNotAligned",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/math/Direction;getOpposite()Lnet/minecraft/util/math/Direction;")
    )
    private Direction onIsTargetNotAligned(Direction direction)
    {
        if (CarpetExtraSettings.repeaterPriorityFix)
            return direction;
        else
            return direction.getOpposite();
    }
}
