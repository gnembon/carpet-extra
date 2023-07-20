package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.HopperBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HopperBlock.class)
public abstract class HopperBlock_syncMixin
{
    @ModifyConstant(method = "neighborUpdate", constant = @Constant(intValue = Block.NO_REDRAW))
    private int onUpdateEnabled(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return original | Block.NOTIFY_LISTENERS;
        else
            return original;
    }
}
