package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.item.ScaffoldingItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ScaffoldingItem.class)
public class ScaffoldingItem_scaffoldingDistance {
    @ModifyConstant(method = "getPlacementContext", constant = @Constant(intValue = 7))
    private static int getPlacementContext_maxDistance(int oldValue)
    {
        return CarpetExtraSettings.scaffoldingDistance;
    }
}
