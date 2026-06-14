package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Spider.class)
public abstract class SpiderEntityMixin extends Monster
{
    protected SpiderEntityMixin(EntityType<? extends Monster> type, Level world)
=======
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends HostileEntity
{
    protected SpiderEntityMixin(EntityType<? extends HostileEntity> type, World world)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        super(type, world);
    }

    @Override
<<<<<<< HEAD
    public void die(DamageSource source)
    {
        if (this.level() instanceof ServerLevel sw)
        {
            if (this.isVehicle() && this.random.nextInt(100) + 1 < CarpetExtraSettings.spiderJockeysDropGapples)
            {
                this.spawnAtLocation(sw, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
            }
            super.die(source);
=======
    public void onDeath(DamageSource source)
    {
        if (this.getEntityWorld() instanceof ServerWorld sw)
        {
            if (this.hasPassengers() && this.random.nextInt(100) + 1 < CarpetExtraSettings.spiderJockeysDropGapples)
            {
                this.dropStack(sw, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
            }
            super.onDeath(source);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
    }
}
