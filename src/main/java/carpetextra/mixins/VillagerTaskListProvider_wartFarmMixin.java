package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.mojang.datafixers.util.Pair;
<<<<<<< HEAD
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.npc.VillagerProfession;
=======
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.VillagerTaskListProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.village.VillagerProfession;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

<<<<<<< HEAD
@Mixin(VillagerGoalPackages.class)
public abstract class VillagerTaskListProvider_wartFarmMixin
{
    @Shadow private static Pair<Integer, BehaviorControl<LivingEntity>> getMinimalLookBehavior() {return null;}

    @Redirect(method = "getWorkPackage", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/npc/VillagerProfession;FARMER:Lnet/minecraft/resources/ResourceKey;"
    ))
    private static ResourceKey<VillagerProfession> redirectFarmer(Holder<VillagerProfession> profession, float speed)
    {
        if (CarpetExtraSettings.clericsFarmWarts && profession.is(VillagerProfession.CLERIC))
=======
@Mixin(VillagerTaskListProvider.class)
public abstract class VillagerTaskListProvider_wartFarmMixin
{
    @Shadow private static Pair<Integer, Task<LivingEntity>> createBusyFollowTask() {return null;}

    @Redirect(method = "createWorkTasks", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/village/VillagerProfession;FARMER:Lnet/minecraft/registry/RegistryKey;"
    ))
    private static RegistryKey<VillagerProfession> redirectFarmer(RegistryEntry<VillagerProfession> profession, float speed)
    {
        if (CarpetExtraSettings.clericsFarmWarts && profession.matchesKey(VillagerProfession.CLERIC))
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            return VillagerProfession.CLERIC;
        return VillagerProfession.FARMER;
    }
}
