package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.world.item.ScaffoldingBlockItem;
=======
import net.minecraft.item.ScaffoldingItem;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

<<<<<<< HEAD
@Mixin(ScaffoldingBlockItem.class)
public class ScaffoldingItem_scaffoldingDistanceMixin {
    @ModifyConstant(method = "updatePlacementContext", constant = @Constant(intValue = 7), require = 0)
=======
@Mixin(ScaffoldingItem.class)
public class ScaffoldingItem_scaffoldingDistanceMixin {
    @ModifyConstant(method = "getPlacementContext", constant = @Constant(intValue = 7), require = 0)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    private int getPlacementContext_maxDistance(int oldValue)
    {
        return CarpetExtraSettings.scaffoldingDistance;
    }
}
