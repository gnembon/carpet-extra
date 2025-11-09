package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.gen.feature.HugeMushroomFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HugeMushroomFeature.class)
public class GiantMushroomReplaceEndPortalFrameMixin {
    @Redirect(
            method = "generateStem(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos$Mutable;Lnet/minecraft/block/BlockState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean redirectIsIn(BlockState state, TagKey<Block> tag) {
        return state.isIn(tag) || (CarpetExtraSettings.giantMushroomReplaceEndPortalFrame && state.isOf(Blocks.END_PORTAL_FRAME));
    }
}
