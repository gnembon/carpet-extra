package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.WoodDelayMultipliers;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractPressurePlateBlock.class)
public abstract class AbstractPressurePlateBlock_variableWoodMixin
{

    @Shadow protected abstract int getTickRate();

    @Redirect(method = "updatePlateState", at = @At(
            value = "INVOKE",
            target ="Lnet/minecraft/block/AbstractPressurePlateBlock;getTickRate()I"
    ))
    private int getCustomTickRate(AbstractPressurePlateBlock block, Entity entity, World world, BlockPos pos, BlockState state, int redstoneLevel)
    {
        if (!CarpetExtraSettings.variableWoodDelays)
        {
            return getTickRate();
        }
        return WoodDelayMultipliers.getForDelay(state.getBlock(), getTickRate());
    }
}
