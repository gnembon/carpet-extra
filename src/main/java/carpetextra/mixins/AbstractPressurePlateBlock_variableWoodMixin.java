package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.WoodDelayMultipliers;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
=======
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

<<<<<<< HEAD
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
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
