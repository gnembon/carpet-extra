package carpetextra.mixins;

<<<<<<< HEAD
=======
import net.minecraft.block.SculkCatalystBlock;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static carpetextra.CarpetExtraSettings.xpPerSculkCatalyst;

<<<<<<< HEAD
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.SculkCatalystBlock;

@Mixin(SculkCatalystBlock.class)
public class SculkCatalystMixin {
    @ModifyArg(
        method = "spawnAfterBreak",
        at = @At(value="INVOKE", target = "Lnet/minecraft/world/level/block/SculkCatalystBlock;tryDropExperience(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/util/valueproviders/IntProvider;)V")
    )
    private IntProvider xpModifier(IntProvider experience) {
        return ConstantInt.of(xpPerSculkCatalyst);
=======
@Mixin(SculkCatalystBlock.class)
public class SculkCatalystMixin {
    @ModifyArg(
        method = "onStacksDropped",
        at = @At(value="INVOKE", target = "Lnet/minecraft/block/SculkCatalystBlock;dropExperienceWhenMined(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/intprovider/IntProvider;)V")
    )
    private IntProvider xpModifier(IntProvider experience) {
        return ConstantIntProvider.create(xpPerSculkCatalyst);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
