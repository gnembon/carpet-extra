package carpetextra.mixins;

import net.minecraft.block.RepeaterBlock;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RepeaterBlock.class)
public abstract class RepeaterBlockMixin {
    @Redirect(
        method = "onUse",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/player/PlayerEntity;abilities:Lnet/minecraft/entity/player/PlayerAbilities;"
        )
    )
    private PlayerAbilities hasPlayerAbilities(final PlayerEntity player) {
        // player will never be null in VANILLA
        if (player == null) return new PlayerAbilities();
        return player.abilities;
    }
}
