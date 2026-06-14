package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Skeleton.class)
public abstract class SkeletonEntityMixin extends AbstractSkeleton
{
    protected SkeletonEntityMixin(EntityType<? extends AbstractSkeleton> type, Level world)
    {
        super(type, world);
    }

    @Override
    public void thunderHit(ServerLevel world, LightningBolt entity)
    {
        if (!world.isClientSide() && !this.isRemoved() && CarpetExtraSettings.renewableWitherSkeletons)
        {
            WitherSkeleton witherSkelly = new WitherSkeleton(EntityType.WITHER_SKELETON, world);
            witherSkelly.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            witherSkelly.finalizeSpawn(world, world.getCurrentDifficultyAt(witherSkelly.blockPosition()), EntitySpawnReason.CONVERSION, null);
            witherSkelly.setNoAi(this.isNoAi());

            if (this.hasCustomName())
            {
                witherSkelly.setCustomName(this.getCustomName());
                witherSkelly.setCustomNameVisible(this.isCustomNameVisible());
            }

            world.addFreshEntity(witherSkelly);

            if (getVehicle() != null)
            {
                Entity mount = getVehicle();
                this.stopRiding();
                witherSkelly.clearFire();
                mount.clearFire();
                witherSkelly.startRiding(mount);
            }
            this.discard();
        }
        else
        {
            super.thunderHit(world, entity);
        }
    }
}
