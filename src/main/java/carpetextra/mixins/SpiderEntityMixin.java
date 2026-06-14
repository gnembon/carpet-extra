package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
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
    {
        super(type, world);
    }

    @Override
    public void die(DamageSource source)
    {
        if (this.level() instanceof ServerLevel sw)
        {
            if (this.isVehicle() && this.random.nextInt(100) + 1 < CarpetExtraSettings.spiderJockeysDropGapples)
            {
                this.spawnAtLocation(sw, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
            }
            super.die(source);
        }
    }
}
