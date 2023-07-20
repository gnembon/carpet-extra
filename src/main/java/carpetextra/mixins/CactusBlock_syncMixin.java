package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.CactusBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CactusBlock.class)
public abstract class CactusBlock_syncMixin
{
    @ModifyConstant(method = "randomTick", require = 2, constant = @Constant(intValue = Block.NO_REDRAW))
    private int onOnScheduledTick(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return original | Block.NOTIFY_LISTENERS;
        else
            return original;
    }
}
