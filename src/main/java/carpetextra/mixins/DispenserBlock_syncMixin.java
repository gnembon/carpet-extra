package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlock_syncMixin
{
    @ModifyConstant(method = "neighborUpdate", constant = @Constant(intValue = 4, ordinal = 0))
    private int onNeighborUpdate1(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return 6;
        else
            return original;
    }
    
    @ModifyConstant(method = "neighborUpdate", constant = @Constant(intValue = 4, ordinal = 1))
    private int onNeighborUpdate2(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return 6;
        else
            return original;
    }
    
}
