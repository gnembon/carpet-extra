package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.WoodDelayMultipliers;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractButtonBlock.class)
public class AbstractButtonBlock_variableWoodMixin
{
    @Redirect(method = "activate", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/AbstractButtonBlock;getTickRate(Lnet/minecraft/world/ViewableWorld;)I"
    ))
    private int getCustomTickRate(AbstractButtonBlock abstractButtonBlock, ViewableWorld viewableWorld_1,
                                  BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1)
    {
        if (!CarpetExtraSettings.variableWoodDelays)
        {
            return abstractButtonBlock.getTickRate(viewableWorld_1);
        }
        return WoodDelayMultipliers.getForDelay(blockState_1.getBlock(), abstractButtonBlock.getTickRate(viewableWorld_1));
    }
}
