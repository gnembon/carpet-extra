package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.WoodDelayMultipliers;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractButtonBlock.class)
public class AbstractButtonBlock_variableWoodMixin
{
    @Redirect(method = "method_21845", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/AbstractButtonBlock;getTickRate(Lnet/minecraft/world/WorldView;)I"
    ))
    private int getCustomTickRate(AbstractButtonBlock abstractButtonBlock, WorldView viewableWorld_1,
                                  BlockState blockState_1, World world_1, BlockPos blockPos_1)
    {
        if (!CarpetExtraSettings.variableWoodDelays)
        {
            return abstractButtonBlock.getTickRate(viewableWorld_1);
        }
        return WoodDelayMultipliers.getForDelay(blockState_1.getBlock(), abstractButtonBlock.getTickRate(viewableWorld_1));
    }
}
