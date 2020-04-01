package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.entity.vehicle.StorageMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(HopperMinecartEntity.class)
public abstract class HopperMinecartEntity_cooldownFixMixin extends StorageMinecartEntity implements Hopper
{
    @Shadow @Final @Mutable
    private BlockPos currentBlockPos;

    public HopperMinecartEntity_cooldownFixMixin(EntityType<? extends HopperMinecartEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
        this.currentBlockPos = BlockPos.ORIGIN;
    }

    //Bugfix 0: Make the cart remember its last Blockpos, otherwise it will always compare to BlockPos.ORIGIN
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/HopperMinecartEntity;setTransferCooldown(I)V",ordinal = 0),locals = LocalCapture.CAPTURE_FAILHARD)
    private void rememberBlockPos(CallbackInfo ci){
        if(CarpetExtraSettings.hopperMinecart8gtCooldown)
            this.currentBlockPos = this.getBlockPos();
        else
            this.currentBlockPos = BlockPos.ORIGIN;
    }

    //Bugfix 1: Picking up an item doesn't set the cooldown because the return value is false even when successful
    @Inject(method = "canOperate", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;extract(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/ItemEntity;)Z", shift = At.Shift.BEFORE),cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void extractAndReturnSuccess(CallbackInfoReturnable<Boolean> cir, List list_1) {
        if (CarpetExtraSettings.hopperMinecart8gtCooldown) {
            boolean result = HopperBlockEntity.extract(this, (ItemEntity) list_1.get(0));
            cir.setReturnValue(result);
            cir.cancel();
        }
    }

    //Bugfix 2: Make the cooldown be 8gt, not 4 (debatable, 4gt might be intended)
    @ModifyConstant(method = "tick",
            constant = @Constant(intValue = 4))
    private int cooldownAmount(int original){
        if(CarpetExtraSettings.hopperMinecart8gtCooldown && original == 4)
            return 8;
        else
            return original;
    }

}
