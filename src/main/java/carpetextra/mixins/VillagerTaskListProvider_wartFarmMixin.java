package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.VillagerTaskListProvider;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VillagerTaskListProvider.class)
public abstract class VillagerTaskListProvider_wartFarmMixin
{
    @Shadow protected static Pair<Integer, Task<LivingEntity>> createBusyFollowTask() {return null;};

    @Redirect(method = "createWorkTasks", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/village/VillagerProfession;FARMER:Lnet/minecraft/village/VillagerProfession;"
    ))
    private static VillagerProfession redirectFarmer(VillagerProfession profession, float f)
    {
        if (CarpetExtraSettings.clericsFarmWarts && profession == VillagerProfession.CLERIC)
            return VillagerProfession.CLERIC;
        return VillagerProfession.FARMER;
    }
}
