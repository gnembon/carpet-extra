package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.world.item.ScaffoldingBlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ScaffoldingBlockItem.class)
public class ScaffoldingItem_scaffoldingDistanceMixin {
    @ModifyConstant(method = "updatePlacementContext", constant = @Constant(intValue = 7), require = 0)
    private int getPlacementContext_maxDistance(int oldValue)
    {
        return CarpetExtraSettings.scaffoldingDistance;
    }
}
