package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Animal.class)
public abstract class AnimalEntity_dispenserMixin
{
    @Shadow /*@Nullable*/ public abstract ServerPlayer getLoveCause();

    @Inject(
            method = "finalizeSpawnChildFromBreeding(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/animal/Animal;Lnet/minecraft/world/entity/AgeableMob;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z",
                    ordinal = 0
            ),
            cancellable = true
    )
    protected void preventDispenserSpawningExperience(ServerLevel world, Animal other, AgeableMob baby, CallbackInfo ci) {
        ServerPlayer serverPlayerEntity_1 = getLoveCause();
        if (serverPlayerEntity_1 == null) {
            serverPlayerEntity_1 = other.getLoveCause();
        }
        if(serverPlayerEntity_1 == null && CarpetExtraSettings.dispensersFeedAnimals) {
            ci.cancel();
        }
    }
}
