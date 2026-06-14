package carpetextra.mixins.griefing;

import carpetextra.CarpetExtraSettings;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Ravager.class)
public abstract class RavagerMixin {
    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object aiStep(GameRules instance, GameRule gameRule) {
        return instance.get(GameRules.MOB_GRIEFING) && !CarpetExtraSettings.disableRavagerGriefing;
    }
    @Redirect(method = "roar", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object roar(GameRules instance, GameRule gameRule) {
        return instance.get(GameRules.MOB_GRIEFING) && !CarpetExtraSettings.disableRavagerGriefing;
    }
}
