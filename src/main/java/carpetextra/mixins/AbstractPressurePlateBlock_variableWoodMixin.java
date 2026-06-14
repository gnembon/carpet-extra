package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.WoodDelayMultipliers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BasePressurePlateBlock.class)
public abstract class AbstractPressurePlateBlock_variableWoodMixin
{

    @Shadow protected abstract int getPressedTime();

    @Redirect(method = "checkPressed", at = @At(
            value = "INVOKE",
            target ="Lnet/minecraft/world/level/block/BasePressurePlateBlock;getPressedTime()I"
    ))
    private int getCustomTickRate(BasePressurePlateBlock block, Entity entity, Level world, BlockPos pos, BlockState state, int redstoneLevel)
    {
        if (!CarpetExtraSettings.variableWoodDelays)
        {
            return getPressedTime();
        }
        return WoodDelayMultipliers.getForDelay(state.getBlock(), getPressedTime());
    }
}
