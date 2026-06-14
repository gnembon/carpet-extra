package carpetextra.mixins;

<<<<<<< HEAD
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.NoteBlock;
=======
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {
    @Redirect(
<<<<<<< HEAD
        method = "useWithoutItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;awardStat(Lnet/minecraft/resources/ResourceLocation;)V"
        )
    )
    private void onIncrementStat(Player player, ResourceLocation ident) {
        // player will never be null in VANILLA
        if (player == null) return;
        player.awardStat(ident);
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        return;
    }
}
