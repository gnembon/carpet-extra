package carpetextra.mixins;

import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ComparatorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ComparatorBlock.class)
public abstract class ComparatorBlock_playerAbilityMixin {

    @Redirect(
        method = "useWithoutItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getAbilities()Lnet/minecraft/world/entity/player/Abilities;"
        )
    )
    private Abilities hasPlayerAbilities(final Player player) {
        // player will never be null in VANILLA
        if (player == null) return new Abilities();
        return player.getAbilities();
    }
}
