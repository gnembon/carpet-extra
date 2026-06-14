package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.DiodeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DiodeBlock.class)
public abstract class AbstractRedstoneGateBlockMixin
{
    @Redirect(
            method = "shouldPrioritize",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/core/Direction;getOpposite()Lnet/minecraft/core/Direction;")
    )
    private Direction onIsTargetNotAligned(Direction direction)
    {
        if (CarpetExtraSettings.repeaterPriorityFix)
            return direction;
        else
            return direction.getOpposite();
    }
}
