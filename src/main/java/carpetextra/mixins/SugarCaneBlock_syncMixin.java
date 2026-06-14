package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SugarCaneBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlock_syncMixin
{
    @ModifyConstant(method = "randomTick", require = 2, constant = @Constant(intValue = Block.UPDATE_NONE))
    private int onOnScheduledTick1(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return original | Block.UPDATE_CLIENTS;
        else
            return original;
    }
}
