package carpetextra.mixins;

import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DaylightDetectorBlock.class)
public abstract class DaylightDetectorBlockMixin {
    @Redirect(
        method = "activate", 
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;canModifyWorld()Z"
        )
    )
    private boolean hasModifyWorld(final PlayerEntity player) {
        if(player == null) return true;
        return player.abilities.allowModifyWorld;
    }
}
