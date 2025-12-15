package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Credits: Salandora
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
{
    public LivingEntityMixin(EntityType<?> entityType_1, Level world_1)
    {
        super(entityType_1, world_1);
    }
    
    @Inject(
            method = "die",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/damagesource/DamageSource;getEntity()Lnet/minecraft/world/entity/Entity;",
                    shift = At.Shift.BEFORE)
    )
    private void convertSandToSoulsand(DamageSource damageSource, CallbackInfo ci)
    {
        if (!CarpetExtraSettings.mobInFireConvertsSandToSoulsand)
            return;
        
        BlockPos pos = BlockPos.containing(this.getX(), this.getY(), this.getZ());
        BlockState statePos = this.level().getBlockState(pos);

        BlockPos below = pos.below(1);
        BlockState stateBelow = this.level().getBlockState(below);
        
        if (statePos.getBlock() == Blocks.FIRE && stateBelow.is(BlockTags.SAND))
        {
            this.level().setBlockAndUpdate(below, Blocks.SOUL_SAND.defaultBlockState());
        }
    }

    @Inject(method = "die", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSource;getEntity()Lnet/minecraft/world/entity/Entity;"))
    private void onOnDeath(DamageSource source, CallbackInfo ci)
    {
        if (this.level() instanceof ServerLevel sw)
        {
            if ((this.getVehicle() instanceof Spider) && this.random.nextInt(100) + 1 < CarpetExtraSettings.spiderJockeysDropGapples)
            {
                this.spawnAtLocation(sw, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
            }
        }
    }
}