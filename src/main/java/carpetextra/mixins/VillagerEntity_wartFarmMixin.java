package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntity_wartFarmMixin extends AbstractTraderEntity
{
    public VillagerEntity_wartFarmMixin(EntityType<? extends AbstractTraderEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Shadow public abstract VillagerData getVillagerData();

    @Inject(method = "canGather", at = @At("HEAD"), cancellable = true)
    private void canClericGather(ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        if (CarpetExtraSettings.clericsFarmWarts && stack.getItem() == Items.NETHER_WART &&
                getVillagerData().getProfession()== VillagerProfession.CLERIC )
        {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "hasSeedToPlant", at = @At("HEAD"), cancellable = true)
    private void hasWartsToPlant(CallbackInfoReturnable<Boolean> cir)
    {
        if (CarpetExtraSettings.clericsFarmWarts && getVillagerData().getProfession()== VillagerProfession.CLERIC )
        {
            cir.setReturnValue(getInventory().containsAny(ImmutableSet.of(Items.NETHER_WART)));
        }
    }
}
