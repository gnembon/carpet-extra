package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.entity.passive.TurtleEntity$MateGoal")
public abstract class TurtleMateGoalMixin extends AnimalMateGoal {


	public TurtleMateGoalMixin(AnimalEntity animalEntity_1, double double_1) {
        super(animalEntity_1, double_1);
    }

    @Inject(
        method = "breed",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            ordinal = 0
        ),
        cancellable = true
    )
    protected void onSpawnExperience(CallbackInfo ci) {
        ServerPlayerEntity serverPlayerEntity_1 = super.animal.getLovingPlayer();
        if (serverPlayerEntity_1 == null) {
            serverPlayerEntity_1 = super.mate.getLovingPlayer();
        }
        if(serverPlayerEntity_1 == null && CarpetExtraSettings.dispensersFeedAnimals) {
            ci.cancel();
        }
    }
}