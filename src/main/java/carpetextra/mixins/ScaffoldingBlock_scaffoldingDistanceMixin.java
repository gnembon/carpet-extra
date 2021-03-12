package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.state.property.IntProperty;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ScaffoldingBlock.class)
public class ScaffoldingBlock_scaffoldingDistanceMixin {
    /* this causes massive problems with vanilla clients and will cause crashes if world is saves with incorrect value
    @Redirect(method = "<clinit>",
                at = @At(value = "FIELD", target = "Lnet/minecraft/state/property/Properties;DISTANCE_0_7:Lnet/minecraft/state/property/IntProperty;", opcode = Opcodes.GETSTATIC)
    )
    private static IntProperty redirectDistanceProperty()
    {
        return IntProperty.of("distance", 0, 64);
    }
     */

    @ModifyConstant(method = "scheduledTick", constant = @Constant(intValue = 7), require = 0)
    private int scheduledTick_maxDistance(int oldValue)
    {
        return CarpetExtraSettings.scaffoldingDistance;
    }

    @ModifyConstant(method = "canPlaceAt", constant = @Constant(intValue = 7), require = 0)
    private int canPlaceAt_maxDistance(int oldValue)
    {
        return CarpetExtraSettings.scaffoldingDistance;
    }

    @ModifyConstant(method = "calculateDistance", constant = @Constant(intValue = 7), require = 0)
    private static int calculateDistance_maxDistance(int oldValue)
    {
        return CarpetExtraSettings.scaffoldingDistance;
    }
}
