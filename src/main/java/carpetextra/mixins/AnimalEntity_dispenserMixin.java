package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
=======
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

<<<<<<< HEAD
@Mixin(Animal.class)
public abstract class AnimalEntity_dispenserMixin
{
    @Shadow /*@Nullable*/ public abstract ServerPlayer getLoveCause();

    @Inject(
            method = "finalizeSpawnChildFromBreeding(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/animal/Animal;Lnet/minecraft/world/entity/AgeableMob;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",
=======
@Mixin(AnimalEntity.class)
public abstract class AnimalEntity_dispenserMixin
{
    @Shadow /*@Nullable*/ public abstract ServerPlayerEntity getLovingPlayer();

    @Inject(
            method = "breed(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;Lnet/minecraft/entity/passive/PassiveEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    ordinal = 0
            ),
            cancellable = true
    )
<<<<<<< HEAD
    protected void preventDispenserSpawningExperience(ServerLevel world, Animal other, AgeableMob baby, CallbackInfo ci) {
        ServerPlayer serverPlayerEntity_1 = getLoveCause();
        if (serverPlayerEntity_1 == null) {
            serverPlayerEntity_1 = other.getLoveCause();
=======
    protected void preventDispenserSpawningExperience(ServerWorld world, AnimalEntity other, PassiveEntity baby, CallbackInfo ci) {
        ServerPlayerEntity serverPlayerEntity_1 = getLovingPlayer();
        if (serverPlayerEntity_1 == null) {
            serverPlayerEntity_1 = other.getLovingPlayer();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
        if(serverPlayerEntity_1 == null && CarpetExtraSettings.dispensersFeedAnimals) {
            ci.cancel();
        }
    }
}
