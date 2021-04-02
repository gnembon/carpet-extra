package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonEntityMixin extends AbstractSkeletonEntity
{
    protected SkeletonEntityMixin(EntityType<? extends AbstractSkeletonEntity> type, World world)
    {
        super(type, world);
    }

    @Override
    public void onStruckByLightning(ServerWorld serverWorld, LightningEntity lightningEntity)
    {
        if (!this.world.isClient && !this.isRemoved() && CarpetExtraSettings.renewableWitherSkeletons)
        {
            WitherSkeletonEntity witherSkelly = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, this.world);
            witherSkelly.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
            witherSkelly.initialize(serverWorld, this.world.getLocalDifficulty(witherSkelly.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);
            witherSkelly.setAiDisabled(this.isAiDisabled());
            
            if (this.hasCustomName())
            {
                witherSkelly.setCustomName(this.getCustomName());
                witherSkelly.setCustomNameVisible(this.isCustomNameVisible());
            }
            
            this.world.spawnEntity(witherSkelly);

            if (getVehicle() != null)
            {
                Entity mount = getVehicle();
                this.stopRiding();
                witherSkelly.extinguish();
                mount.extinguish();
                witherSkelly.startRiding(mount, true);
            }
            this.discard();
        }
        else
        {
            super.onStruckByLightning(serverWorld,lightningEntity);
        }
    }
}
