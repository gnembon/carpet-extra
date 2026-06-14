package carpetextra.mixins;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.DaylightDetectorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DaylightDetectorBlock.class)
public abstract class DaylightDetectorBlockMixin {
    @Redirect(
        method = "useWithoutItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;mayBuild()Z"
        )
    )
    private boolean hasModifyWorld(final Player player) {
        // player will never be null in VANILLA
        if (player == null) return true;
        return player.mayBuild();
    }
}
