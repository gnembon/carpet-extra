package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SecondaryPoiSensor;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
=======
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SecondaryPointsOfInterestSensor;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.VillagerProfession;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

<<<<<<< HEAD
@Mixin(SecondaryPoiSensor.class)
=======
@Mixin(SecondaryPointsOfInterestSensor.class)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
public class SecondaryPointOfInterestSensor_wartFarmMixin
{
    // this is just to prevent clerics wandering to soul sand if VANILLA
    // this might come in handy in general making sure villagers don't check 405 blocks around them every 2 seconds
    // for nothing, but hey?
<<<<<<< HEAD
    @Inject(method = "doTick", at = @At("HEAD"), cancellable = true)
    private void notVanillaCleric(ServerLevel world, Villager villager, CallbackInfo ci)
    {
        if(!CarpetExtraSettings.clericsFarmWarts && villager.getVillagerData().profession().is(VillagerProfession.CLERIC))
        {
            // in vanilla we want not to find secondary POI for clerics
            Brain<?> brain = villager.getBrain();
            brain.eraseMemory(MemoryModuleType.SECONDARY_JOB_SITE);
=======
    @Inject(method = "sense", at = @At("HEAD"), cancellable = true)
    private void notVanillaCleric(ServerWorld world, VillagerEntity villager, CallbackInfo ci)
    {
        if(!CarpetExtraSettings.clericsFarmWarts && villager.getVillagerData().profession().matchesKey(VillagerProfession.CLERIC))
        {
            // in vanilla we want not to find secondary POI for clerics
            Brain<?> brain = villager.getBrain();
            brain.forget(MemoryModuleType.SECONDARY_JOB_SITE);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            ci.cancel();

        }
    }
}
