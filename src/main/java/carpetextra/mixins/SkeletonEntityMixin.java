package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
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
    public void onStruckByLightning(LightningEntity lightning)
    {
        if (!this.world.isClient && !this.removed && CarpetExtraSettings.renewableWitherSkeletons)
        {
            WitherSkeletonEntity witherSkelly = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, this.world);
            witherSkelly.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
            witherSkelly.initialize(this.world, this.world.getLocalDifficulty(witherSkelly.getBlockPos()), SpawnType.CONVERSION, (EntityData) null, (CompoundTag) null);
            witherSkelly.setAiDisabled(this.isAiDisabled());
            
            if (this.hasCustomName())
            {
                witherSkelly.setCustomName(this.getCustomName());
                witherSkelly.setCustomNameVisible(this.isCustomNameVisible());
            }
            
            this.world.spawnEntity(witherSkelly);
            this.remove();
        }
        else
        {
            super.onStruckByLightning(lightning);
        }
    }
}
