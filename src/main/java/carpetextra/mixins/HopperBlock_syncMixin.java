package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.HopperBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HopperBlock.class)
public abstract class HopperBlock_syncMixin
{
    @ModifyConstant(method = "updateEnabled", constant = @Constant(intValue = 4))
    private int onUpdateEnabled(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return 2;
        else
            return original;
    }
}
