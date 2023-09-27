package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SecondaryPointsOfInterestSensor;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SecondaryPointsOfInterestSensor.class)
public class SecondaryPointOfInterestSensor_wartFarmMixin
{
    // this is just to prevent clerics wandering to soul sand if VANILLA
    // this might come in handy in general making sure villagers don't check 405 blocks around them every 2 seconds
    // for nothing, but hey?
    @Inject(method = "sense", at = @At("HEAD"), cancellable = true)
    private void notVanillaCleric(ServerWorld world, VillagerEntity villager, CallbackInfo ci)
    {
        if(!CarpetExtraSettings.clericsFarmWarts && villager.getVillagerData().getProfession() == VillagerProfession.CLERIC)
        {
            // in vanilla we want not to find secondary POI for clerics
            Brain<?> brain = villager.getBrain();
            brain.forget(MemoryModuleType.SECONDARY_JOB_SITE);
            ci.cancel();

        }
    }
}
