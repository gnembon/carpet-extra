package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.WoodDelayMultipliers;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
=======
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ButtonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ButtonBlock.class)
public abstract class AbstractButtonBlock_variableWoodMixin
{
<<<<<<< HEAD
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
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }


}
