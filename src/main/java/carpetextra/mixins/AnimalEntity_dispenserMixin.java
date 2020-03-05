package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
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
            method = "breed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
                    ordinal = 1
            ),
            cancellable = true
    )
    protected void onSpawnExperience(World world, AnimalEntity animalEntity, CallbackInfo ci) {
        ServerPlayerEntity serverPlayerEntity_1 = getLovingPlayer();
        if (serverPlayerEntity_1 == null) {
            serverPlayerEntity_1 = animalEntity.getLovingPlayer();
        }
        if(serverPlayerEntity_1 == null && CarpetExtraSettings.dispensersFeedAnimals) {
            ci.cancel();
        }
    }
}
