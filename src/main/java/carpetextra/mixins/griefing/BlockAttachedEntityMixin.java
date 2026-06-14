package carpetextra.mixins.griefing;

import carpetextra.CarpetExtraSettings;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockAttachedEntity.class)
public abstract class BlockAttachedEntityMixin {
    @Redirect(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object hurtServer(GameRules instance, GameRule gameRule) {
        return instance.get(GameRules.MOB_GRIEFING) && !CarpetExtraSettings.disableCreeperGriefing;
    }
}
