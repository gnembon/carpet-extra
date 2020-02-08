package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Shadow public abstract Box getBoundingBox();
    
    @Shadow protected abstract ListTag toListTag(double... doubles_1);
    
    @Shadow protected abstract boolean shouldSetPositionOnLoad();
    
    @Shadow public double x;
    
    @Shadow public double y;
    
    @Shadow public double z;
    
    @Shadow public abstract void setBoundingBox(Box box_1);

    @Shadow protected abstract void refreshPosition();

    @Inject(
            method = "toTag",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0,
                    target = "Lnet/minecraft/nbt/CompoundTag;put(Ljava/lang/String;Lnet/minecraft/nbt/Tag;)Lnet/minecraft/nbt/Tag;")
    )
    private void onToTag(CompoundTag compoundTag_1, CallbackInfoReturnable<CompoundTag> cir)
    {
        if (CarpetExtraSettings.reloadSuffocationFix)
        {
            Box box = this.getBoundingBox();
            compoundTag_1.put("CM_Box", this.toListTag(box.x1, box.y1, box.z1, box.x2, box.y2, box.z2));
        }
    }
    
    @Redirect(method = "fromTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;shouldSetPositionOnLoad()Z"))
    private boolean cancelShouldSetPositionOnLoad(Entity entity)
    {
        return false;
    }
    
    @Inject(
            method = "fromTag",
            at = @At(value = "INVOKE", shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/entity/Entity;readCustomDataFromTag(Lnet/minecraft/nbt/CompoundTag;)V")
    )
    private void onFromTag(CompoundTag compoundTag_1, CallbackInfo ci)
    {
        if (this.shouldSetPositionOnLoad())
        {
            this.refreshPosition();
        }
        
        if (CarpetExtraSettings.reloadSuffocationFix && compoundTag_1.contains("CM_Box", 9))
        {
            ListTag box_tag = compoundTag_1.getList("CM_Box", 6);
            
            Box box = new Box(box_tag.getDouble(0), box_tag.getDouble(1),
                    box_tag.getDouble(2), box_tag.getDouble(3),
                    box_tag.getDouble(4), box_tag.getDouble(5));
    
            double deltaX = ((box.x1 + box.x2) / 2.0D) - this.x;
            double deltaY = box.y1 - this.y;
            double deltaZ = ((box.z1 + box.z2) / 2.0D) - this.z;
            
            // Credits: MrGrim (MUP) -> Sanity check.
            // If the position and BoundingBox center point are > 0.1 blocks apart then do not restore the BoundingBox. In vanilla
            // this should never happen, but mods might not be aware that the BoundingBox is stored and that the entity
            // position will be reset to it.
            if (((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ)) < 0.01D)
            {
                this.setBoundingBox(box);
            }
        }
    }
    
}