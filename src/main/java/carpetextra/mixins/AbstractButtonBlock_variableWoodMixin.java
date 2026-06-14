package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.WoodDelayMultipliers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ButtonBlock.class)
public abstract class AbstractButtonBlock_variableWoodMixin
{
    @Redirect(method = "press", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;scheduleTick(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;I)V"
    ))
    private void getCustomTickRate(Level instance, BlockPos blockPos, Block block, int i,
                                   BlockState state, Level world, BlockPos pos)
    {
        if (!CarpetExtraSettings.variableWoodDelays)
        {
            instance.scheduleTick(blockPos, block, i);
            return;
        }
        instance.scheduleTick(blockPos, block,  WoodDelayMultipliers.getForDelay(state.getBlock(), i));
    }

    @Redirect(method = "checkPressed", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;scheduleTick(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;I)V"
    ))
    private void getCustomTickRateProjectiles(Level instance, BlockPos blockPos, Block block, int i,
                                   BlockState state, Level world, BlockPos pos)
    {
        if (!CarpetExtraSettings.variableWoodDelays)
        {
            instance.scheduleTick(blockPos, block, i);
            return;
        }
        instance.scheduleTick(blockPos, block,  WoodDelayMultipliers.getForDelay(state.getBlock(), i));
    }


}
