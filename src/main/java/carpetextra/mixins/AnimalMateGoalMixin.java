package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalMateGoal.class)
public abstract class AnimalMateGoalMixin {

    @Shadow @Final
    protected AnimalEntity animal;

    @Shadow
    protected AnimalEntity mate;

    @Inject(
        method = "breed",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            shift = At.Shift.AFTER,
            ordinal = 1
        ),
        cancellable = true
    )
    protected void onSpawnExperience(CallbackInfo ci) {
        ServerPlayerEntity serverPlayerEntity_1 = this.animal.getLovingPlayer();
        if (serverPlayerEntity_1 == null) {
            serverPlayerEntity_1 = this.mate.getLovingPlayer();
        }
        if(serverPlayerEntity_1 == null && CarpetExtraSettings.dispensersFeedAnimals) {
            ci.cancel();
        }
    }
}