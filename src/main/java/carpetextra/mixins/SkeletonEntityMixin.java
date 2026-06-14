package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
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
=======
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonEntityMixin extends AbstractSkeletonEntity
{
    protected SkeletonEntityMixin(EntityType<? extends AbstractSkeletonEntity> type, World world)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        super(type, world);
    }

    @Override
<<<<<<< HEAD
    public void thunderHit(ServerLevel world, LightningBolt entity)
    {
        if (!world.isClientSide() && !this.isRemoved() && CarpetExtraSettings.renewableWitherSkeletons)
        {
            WitherSkeleton witherSkelly = new WitherSkeleton(EntityType.WITHER_SKELETON, world);
            witherSkelly.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            witherSkelly.finalizeSpawn(world, world.getCurrentDifficultyAt(witherSkelly.blockPosition()), EntitySpawnReason.CONVERSION, null);
            witherSkelly.setNoAi(this.isNoAi());
=======
    public void onStruckByLightning(ServerWorld world, LightningEntity entity)
    {
        if (!world.isClient() && !this.isRemoved() && CarpetExtraSettings.renewableWitherSkeletons)
        {
            WitherSkeletonEntity witherSkelly = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, world);
            witherSkelly.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            witherSkelly.initialize(world, world.getLocalDifficulty(witherSkelly.getBlockPos()), SpawnReason.CONVERSION, null);
            witherSkelly.setAiDisabled(this.isAiDisabled());
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

            if (this.hasCustomName())
            {
                witherSkelly.setCustomName(this.getCustomName());
                witherSkelly.setCustomNameVisible(this.isCustomNameVisible());
            }

<<<<<<< HEAD
            world.addFreshEntity(witherSkelly);
=======
            world.spawnEntity(witherSkelly);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

            if (getVehicle() != null)
            {
                Entity mount = getVehicle();
                this.stopRiding();
<<<<<<< HEAD
                witherSkelly.clearFire();
                mount.clearFire();
=======
                witherSkelly.extinguish();
                mount.extinguish();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                witherSkelly.startRiding(mount);
            }
            this.discard();
        }
        else
        {
<<<<<<< HEAD
            super.thunderHit(world, entity);
=======
            super.onStruckByLightning(world, entity);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
    }
}
