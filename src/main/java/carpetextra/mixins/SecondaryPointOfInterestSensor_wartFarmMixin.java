package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SecondaryPoiSensor;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SecondaryPoiSensor.class)
public class SecondaryPointOfInterestSensor_wartFarmMixin
{
    // this is just to prevent clerics wandering to soul sand if VANILLA
    // this might come in handy in general making sure villagers don't check 405 blocks around them every 2 seconds
    // for nothing, but hey?
    @Inject(method = "doTick", at = @At("HEAD"), cancellable = true)
    private void notVanillaCleric(ServerLevel world, Villager villager, CallbackInfo ci)
    {
        if(!CarpetExtraSettings.clericsFarmWarts && villager.getVillagerData().profession().is(VillagerProfession.CLERIC))
        {
            // in vanilla we want not to find secondary POI for clerics
            Brain<?> brain = villager.getBrain();
            brain.eraseMemory(MemoryModuleType.SECONDARY_JOB_SITE);
            ci.cancel();

        }
    }
}
