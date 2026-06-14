package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.google.common.collect.ImmutableSet;
<<<<<<< HEAD
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
=======
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

<<<<<<< HEAD
@Mixin(Villager.class)
public abstract class VillagerEntity_wartFarmMixin extends AbstractVillager
{
    public VillagerEntity_wartFarmMixin(EntityType<? extends AbstractVillager> entityType, Level world)
=======
@Mixin(VillagerEntity.class)
public abstract class VillagerEntity_wartFarmMixin extends MerchantEntity
{
    public VillagerEntity_wartFarmMixin(EntityType<? extends MerchantEntity> entityType, World world)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        super(entityType, world);
    }

    @Shadow public abstract VillagerData getVillagerData();

<<<<<<< HEAD
    @Inject(method = "wantsToPickUp", at = @At("HEAD"), cancellable = true)
    private void canClericGather(ServerLevel world, ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        if (CarpetExtraSettings.clericsFarmWarts && stack.getItem() == Items.NETHER_WART &&
                getVillagerData().profession().is(VillagerProfession.CLERIC) )
=======
    @Inject(method = "canGather", at = @At("HEAD"), cancellable = true)
    private void canClericGather(ServerWorld world, ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        if (CarpetExtraSettings.clericsFarmWarts && stack.getItem() == Items.NETHER_WART &&
                getVillagerData().profession().matchesKey(VillagerProfession.CLERIC) )
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        {
            cir.setReturnValue(true);
        }
    }

<<<<<<< HEAD
    @Inject(method = "hasFarmSeeds", at = @At("HEAD"), cancellable = true)
    private void hasWartsToPlant(CallbackInfoReturnable<Boolean> cir)
    {
        if (CarpetExtraSettings.clericsFarmWarts && getVillagerData().profession().is(VillagerProfession.CLERIC ))
        {
            cir.setReturnValue(getInventory().hasAnyOf(ImmutableSet.of(Items.NETHER_WART)));
=======
    @Inject(method = "hasSeedToPlant", at = @At("HEAD"), cancellable = true)
    private void hasWartsToPlant(CallbackInfoReturnable<Boolean> cir)
    {
        if (CarpetExtraSettings.clericsFarmWarts && getVillagerData().profession().matchesKey(VillagerProfession.CLERIC ))
        {
            cir.setReturnValue(getInventory().containsAny(ImmutableSet.of(Items.NETHER_WART)));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
    }
}
