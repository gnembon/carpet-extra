package carpetextra.mixins;

import net.minecraft.block.NoteBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
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
        // player will never be null in VANILLA
        if (player == null) return;
        player.incrementStat(ident);
        return;
    }

    @Redirect(
            method = "onUse",
            at = @At(
                value = "INVOKE",
                target = "net/minecraft/entity/player/PlayerEntity.getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"
            )
        )
        private ItemStack onGetItemInHand(PlayerEntity player, Hand hand) {
            // player will never be null in VANILLA
            if (player == null) return ItemStack.EMPTY;
            return player.getStackInHand(hand);
        }
}
