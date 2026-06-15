package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LavaFluid.class)
public abstract class LavaFluidMixin {
    @Redirect(method = "spreadTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private boolean convertDeepslate(LevelAccessor instance, BlockPos blockPos, BlockState state, int i) {
        if (CarpetExtraSettings.renewableDeepslate && blockPos.getY() < 0) {
            return instance.setBlock(blockPos, Blocks.DEEPSLATE.defaultBlockState(), i);
        }
        return instance.setBlock(blockPos, state, i);
    }
}
