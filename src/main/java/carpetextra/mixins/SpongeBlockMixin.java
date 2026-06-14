package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.world.level.block.SpongeBlock;
=======
import net.minecraft.block.SpongeBlock;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SpongeBlock.class)
public abstract class SpongeBlockMixin {

    @ModifyConstant(
<<<<<<< HEAD
        method = "removeWaterBreadthFirstSearch",
=======
        method = "absorbWater",
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        constant = @Constant(intValue = 65)
    )
    private int onCheckBlockLimit(int blockLimit) {
        return CarpetExtraSettings.maxSpongeSuck + 1;
    }

    
    @ModifyConstant(
<<<<<<< HEAD
        method = "removeWaterBreadthFirstSearch",
=======
        method = "absorbWater",
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        constant = @Constant(intValue = 6)
    )
    private int onCheckOffsetLimit(int offsetLimit) {
        return CarpetExtraSettings.maxSpongeRange - 1;
    }
}
