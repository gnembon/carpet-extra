package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.WoodDelayMultipliers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ButtonBlock.class)
public abstract class AbstractButtonBlock_variableWoodMixin
{
    @Redirect(method = "powerOn", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;scheduleBlockTick(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;I)V"
    ))
    private void getCustomTickRate(World instance, BlockPos blockPos, Block block, int i,
                                   BlockState state, World world, BlockPos pos)
    {
        if (!CarpetExtraSettings.variableWoodDelays)
        {
            instance.scheduleBlockTick(blockPos, block, i);
            return;
        }
        instance.scheduleBlockTick(blockPos, block,  WoodDelayMultipliers.getForDelay(state.getBlock(), i));
    }

    @Redirect(method = "tryPowerWithProjectiles", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;scheduleBlockTick(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;I)V"
    ))
    private void getCustomTickRateProjectiles(World instance, BlockPos blockPos, Block block, int i,
                                   BlockState state, World world, BlockPos pos)
    {
        if (!CarpetExtraSettings.variableWoodDelays)
        {
            instance.scheduleBlockTick(blockPos, block, i);
            return;
        }
        instance.scheduleBlockTick(blockPos, block,  WoodDelayMultipliers.getForDelay(state.getBlock(), i));
    }


}
