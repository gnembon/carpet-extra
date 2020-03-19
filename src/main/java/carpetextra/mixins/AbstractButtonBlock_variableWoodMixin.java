package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.WoodDelayMultipliers;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractButtonBlock.class)
public abstract class AbstractButtonBlock_variableWoodMixin
{
    @Shadow protected abstract int method_26153();

    @Redirect(method = "powerOn", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/AbstractButtonBlock;method_26153()I"  // getTickRate
    ))
    private int getCustomTickRate(AbstractButtonBlock abstractButtonBlock,
                                  BlockState blockState_1, World world_1, BlockPos blockPos_1)
    {
        if (!CarpetExtraSettings.variableWoodDelays)
        {
            return method_26153();
        }
        return WoodDelayMultipliers.getForDelay(blockState_1.getBlock(), method_26153());
    }
}
