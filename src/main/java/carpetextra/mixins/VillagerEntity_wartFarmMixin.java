package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerEntity_wartFarmMixin extends AbstractVillager
{
    public VillagerEntity_wartFarmMixin(EntityType<? extends AbstractVillager> entityType, Level world)
    {
        super(entityType, world);
    }

    @Shadow public abstract VillagerData getVillagerData();

    @Inject(method = "wantsToPickUp", at = @At("HEAD"), cancellable = true)
    private void canClericGather(ServerLevel world, ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        if (CarpetExtraSettings.clericsFarmWarts && stack.getItem() == Items.NETHER_WART &&
                getVillagerData().profession().is(VillagerProfession.CLERIC) )
        {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "hasFarmSeeds", at = @At("HEAD"), cancellable = true)
    private void hasWartsToPlant(CallbackInfoReturnable<Boolean> cir)
    {
        if (CarpetExtraSettings.clericsFarmWarts && getVillagerData().profession().is(VillagerProfession.CLERIC ))
        {
            cir.setReturnValue(getInventory().hasAnyOf(ImmutableSet.of(Items.NETHER_WART)));
        }
    }
}
