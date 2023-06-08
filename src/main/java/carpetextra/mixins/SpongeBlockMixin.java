package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.SpongeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SpongeBlock.class)
public abstract class SpongeBlockMixin {

    @ModifyConstant(
        method = "absorbWater",
        constant = @Constant(intValue = 65)
    )
    private int onCheckBlockLimit(int blockLimit) {
        return CarpetExtraSettings.maxSpongeSuck + 1;
    }

    
    @ModifyConstant(
        method = "absorbWater",
        constant = @Constant(intValue = 6)
    )
    private int onCheckOffsetLimit(int offsetLimit) {
        return CarpetExtraSettings.maxSpongeRange - 1;
    }
}
