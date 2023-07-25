package carpetextra.mixins;

import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DaylightDetectorBlock.class)
public abstract class DaylightDetectorBlockMixin {
    @Redirect(
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;canModifyBlocks()Z"
        )
    )
    private boolean hasModifyWorld(final PlayerEntity player) {
        // player will never be null in VANILLA
        if (player == null) return true;
        return player.canModifyBlocks();
    }
}
