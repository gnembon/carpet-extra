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
    private int getCustomTickRate(AbstractPressurePlateBlock abstractPressurePlateBlock,
                                  Entity entity, World world_1, BlockPos blockPos_1, BlockState blockState_1, int int_1)
    {
        if (!CarpetExtraSettings.variableWoodDelays)
        {
            return getTickRate();
        }
        return WoodDelayMultipliers.getForDelay(blockState_1.getBlock(), getTickRate());
    }
}
