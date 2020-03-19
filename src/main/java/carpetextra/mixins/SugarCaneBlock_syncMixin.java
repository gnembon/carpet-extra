package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.SugarCaneBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlock_syncMixin
{
    @ModifyConstant(method = "randomTick", require = 2, constant = @Constant(intValue = 4))
    private int onOnScheduledTick1(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return 6;
        else
            return original;
    }
}
