package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {
    @Redirect(method = "shouldSpreadLiquid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z",  ordinal = 0))
    private boolean convertCobbledDeepslate(Level instance, BlockPos pos, BlockState blockState) {
        if (CarpetExtraSettings.renewableDeepslate && pos.getY() < 0 && blockState.is(Blocks.COBBLESTONE)) {
            return instance.setBlockAndUpdate(pos, Blocks.COBBLED_DEEPSLATE.defaultBlockState());
        }
        return instance.setBlockAndUpdate(pos, blockState);
    }
}
