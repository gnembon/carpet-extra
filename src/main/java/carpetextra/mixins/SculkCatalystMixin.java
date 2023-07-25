package carpetextra.mixins;

import net.minecraft.block.SculkCatalystBlock;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static carpetextra.CarpetExtraSettings.xpPerSculkCatalyst;

@Mixin(SculkCatalystBlock.class)
public class SculkCatalystMixin {
    @ModifyArg(
        method = "onStacksDropped",
        at = @At(value="INVOKE", target = "Lnet/minecraft/block/SculkCatalystBlock;dropExperienceWhenMined(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/intprovider/IntProvider;)V")
    )
    private IntProvider xpModifier(IntProvider experience) {
        return ConstantIntProvider.create(xpPerSculkCatalyst);
    }
}
