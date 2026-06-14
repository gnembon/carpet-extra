package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static carpetextra.CarpetExtraSettings.xpPerSculkCatalyst;

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
    }
}
