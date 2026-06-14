package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.world.level.block.ScaffoldingBlock;
=======
import net.minecraft.block.ScaffoldingBlock;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

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

<<<<<<< HEAD
    @ModifyConstant(method = "tick", constant = @Constant(intValue = 7), require = 0)
=======
    @ModifyConstant(method = "scheduledTick", constant = @Constant(intValue = 7), require = 0)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    private int scheduledTick_maxDistance(int oldValue)
    {
        return CarpetExtraSettings.scaffoldingDistance;
    }

<<<<<<< HEAD
    @ModifyConstant(method = "canSurvive", constant = @Constant(intValue = 7), require = 0)
=======
    @ModifyConstant(method = "canPlaceAt", constant = @Constant(intValue = 7), require = 0)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    private int canPlaceAt_maxDistance(int oldValue)
    {
        return CarpetExtraSettings.scaffoldingDistance;
    }

<<<<<<< HEAD
    @ModifyConstant(method = "getDistance", constant = @Constant(intValue = 7), require = 0)
=======
    @ModifyConstant(method = "calculateDistance", constant = @Constant(intValue = 7), require = 0)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    private static int calculateDistance_maxDistance(int oldValue)
    {
        return CarpetExtraSettings.scaffoldingDistance;
    }
}
