package carpetextra.mixins;

<<<<<<< HEAD
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.RepeaterBlock;
=======
import net.minecraft.block.RepeaterBlock;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RepeaterBlock.class)
public abstract class RepeaterBlockMixin {
    @Redirect(
<<<<<<< HEAD
        method = "useWithoutItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getAbilities()Lnet/minecraft/world/entity/player/Abilities;"
        )
    )
    private Abilities hasPlayerAbilities(final Player player) {
        // player will never be null in VANILLA
        if (player == null) return new Abilities();
=======
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;getAbilities()Lnet/minecraft/entity/player/PlayerAbilities;"
        )
    )
    private PlayerAbilities hasPlayerAbilities(final PlayerEntity player) {
        // player will never be null in VANILLA
        if (player == null) return new PlayerAbilities();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        return player.getAbilities();
    }
}
