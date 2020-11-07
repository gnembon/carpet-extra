package carpetextra.mixins;

import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RedstoneWireBlock.class)
public abstract class RedstoneWireBlockMixin
{
    @Redirect(
            method = "onUse",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getAbilities()Lnet/minecraft/entity/player/PlayerAbilities;"
            )
    )
    private PlayerAbilities hasPlayerAbilities(final PlayerEntity player)
    {
        // player will never be null in VANILLA
        if (player == null) return new PlayerAbilities();
        return player.getAbilities();
    }
}
