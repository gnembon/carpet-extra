package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.SaplingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlock_syncMixin
{
    @ModifyConstant(method = "generate", constant = @Constant(intValue = 4))
    private int onGenerate(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return 6;
        else
            return original;
    }
}
