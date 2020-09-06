package carpetextra.mixins;

import net.minecraft.block.NoteBlock;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {
    @Redirect(
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/util/Identifier;)V"
        )
    )
    private void onIncrementStat(PlayerEntity player, Identifier ident) {
        if(player == null) return;
        player.incrementStat(ident);
        return;
    }
}
