package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.entity.animal.fox.Fox$FoxBreedGoal")
public abstract class FoxMateGoalMixin extends BreedGoal {


	public FoxMateGoalMixin(Animal fox, double speed) {
        super(fox, speed);
    }

    @Inject(
        method = "breed()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",
            ordinal = 0
        ),
        cancellable = true
    )
    protected void preventDispenserSpawningExperience(CallbackInfo ci) {
        ServerPlayer player = super.animal.getLoveCause();
        if (player == null && super.partner != null) {
            player = super.partner.getLoveCause();
        }
        if (player == null && CarpetExtraSettings.dispensersFeedAnimals) {
            ci.cancel();
        }
    }
}