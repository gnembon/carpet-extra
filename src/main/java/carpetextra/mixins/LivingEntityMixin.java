package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
=======
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Credits: Salandora
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
{
<<<<<<< HEAD
    public LivingEntityMixin(EntityType<?> entityType_1, Level world_1)
=======
    public LivingEntityMixin(EntityType<?> entityType_1, World world_1)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        super(entityType_1, world_1);
    }
    
    @Inject(
<<<<<<< HEAD
            method = "die",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/damagesource/DamageSource;getEntity()Lnet/minecraft/world/entity/Entity;",
=======
            method = "onDeath",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/damage/DamageSource;getAttacker()Lnet/minecraft/entity/Entity;",
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    shift = At.Shift.BEFORE)
    )
    private void convertSandToSoulsand(DamageSource damageSource, CallbackInfo ci)
    {
        if (!CarpetExtraSettings.mobInFireConvertsSandToSoulsand)
            return;
        
<<<<<<< HEAD
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
=======
        BlockPos pos = BlockPos.ofFloored(this.getX(), this.getY(), this.getZ());
        BlockState statePos = this.getEntityWorld().getBlockState(pos);

        BlockPos below = pos.down(1);
        BlockState stateBelow = this.getEntityWorld().getBlockState(below);
        
        if (statePos.getBlock() == Blocks.FIRE && stateBelow.isIn(BlockTags.SAND))
        {
            this.getEntityWorld().setBlockState(below, Blocks.SOUL_SAND.getDefaultState());
        }
    }

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;getAttacker()Lnet/minecraft/entity/Entity;"))
    private void onOnDeath(DamageSource source, CallbackInfo ci)
    {
        if (this.getEntityWorld() instanceof ServerWorld sw)
        {
            if ((this.getVehicle() instanceof SpiderEntity) && this.random.nextInt(100) + 1 < CarpetExtraSettings.spiderJockeysDropGapples)
            {
                this.dropStack(sw, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            }
        }
    }
}