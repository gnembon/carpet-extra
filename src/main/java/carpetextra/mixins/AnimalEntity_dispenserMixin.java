package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntity_dispenserMixin
{
    @Shadow /*@Nullable*/ public abstract ServerPlayerEntity getLovingPlayer();

    @Inject(
            method = "breed(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;Lnet/minecraft/entity/passive/PassiveEntity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
                    ordinal = 0
            ),
            cancellable = true
    )
    protected void preventDispenserSpawningExperience(ServerWorld world, AnimalEntity other, PassiveEntity baby, CallbackInfo ci) {
        ServerPlayerEntity serverPlayerEntity_1 = getLovingPlayer();
        if (serverPlayerEntity_1 == null) {
            serverPlayerEntity_1 = other.getLovingPlayer();
        }
        if(serverPlayerEntity_1 == null && CarpetExtraSettings.dispensersFeedAnimals) {
            ci.cancel();
        }
    }
}
