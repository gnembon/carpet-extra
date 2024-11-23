package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
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
    {
        super(type, world);
    }

    @Override
    public void onDeath(DamageSource source)
    {
        if (this.getWorld() instanceof ServerWorld sw)
        {
            if (this.hasPassengers() && this.random.nextInt(100) + 1 < CarpetExtraSettings.spiderJockeysDropGapples)
            {
                this.dropStack(sw, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
            }
            super.onDeath(source);
        }
    }
}
