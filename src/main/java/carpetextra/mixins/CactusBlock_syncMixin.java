package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.CactusBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CactusBlock.class)
public abstract class CactusBlock_syncMixin
{
    @ModifyConstant(method = "scheduledTick", constant = @Constant(intValue = 4, ordinal = 0))
    private int onOnScheduledTick1(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return 6;
        else
            return original;
    }
    
    @ModifyConstant(method = "scheduledTick", constant = @Constant(intValue = 4, ordinal = 1))
    private int onOnScheduledTick2(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return 6;
        else
            return original;
    }
}
