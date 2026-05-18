package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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
            return VillagerProfession.CLERIC;
        return VillagerProfession.FARMER;
    }
}
