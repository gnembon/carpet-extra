package carpetextra.mixins;

<<<<<<< HEAD
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.DaylightDetectorBlock;
=======
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.entity.player.PlayerEntity;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DaylightDetectorBlock.class)
public abstract class DaylightDetectorBlockMixin {
    @Redirect(
<<<<<<< HEAD
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
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
