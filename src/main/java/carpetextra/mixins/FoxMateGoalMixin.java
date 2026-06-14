package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
=======
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.network.ServerPlayerEntity;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

<<<<<<< HEAD
@Mixin(targets = "net.minecraft.world.entity.animal.Fox$FoxBreedGoal")
public abstract class FoxMateGoalMixin extends BreedGoal {


	public FoxMateGoalMixin(Animal fox, double speed) {
=======
@Mixin(targets = "net.minecraft.entity.passive.FoxEntity$MateGoal")
public abstract class FoxMateGoalMixin extends AnimalMateGoal {


	public FoxMateGoalMixin(AnimalEntity fox, double speed) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        super(fox, speed);
    }

    @Inject(
        method = "breed()V",
        at = @At(
            value = "INVOKE",
<<<<<<< HEAD
            target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",
=======
            target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            ordinal = 0
        ),
        cancellable = true
    )
    protected void preventDispenserSpawningExperience(CallbackInfo ci) {
<<<<<<< HEAD
        ServerPlayer player = super.animal.getLoveCause();
        if (player == null && super.partner != null) {
            player = super.partner.getLoveCause();
=======
        ServerPlayerEntity player = super.animal.getLovingPlayer();
        if (player == null && super.mate != null) {
            player = super.mate.getLovingPlayer();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
        if (player == null && CarpetExtraSettings.dispensersFeedAnimals) {
            ci.cancel();
        }
    }
}