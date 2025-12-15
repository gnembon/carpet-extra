package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueOutput.TypedOutputList;
import net.minecraft.world.phys.AABB;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.serialization.Codec;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Shadow public abstract AABB getBoundingBox();
    
    //@Shadow protected abstract NbtList toNbtList(double... doubles_1);
    
    @Shadow protected abstract boolean repositionEntityAfterLoad();
    
    @Shadow public abstract double getX();
    
    @Shadow public abstract double getY();
    
    @Shadow public abstract double getZ();
    
    @Shadow public abstract void setBoundingBox(AABB box_1);

    @Shadow protected abstract void reapplyPosition();

    @Unique
    protected void fillDoubleAppender(TypedOutputList<Double> list, double... values) {
        for (final double value : values) {
            list.add(value);
        }
    }

    @Inject(
            method = "saveWithoutId",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0,
                    target = "Lnet/minecraft/world/level/storage/ValueOutput;putDouble(Ljava/lang/String;D)V")
    )
    private void onToTag(ValueOutput view, CallbackInfo ci)
    {
        if (CarpetExtraSettings.reloadSuffocationFix)
        {
            AABB box = this.getBoundingBox();
            fillDoubleAppender(view.list("CM_Box", Codec.DOUBLE), box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
        }
    }
    
    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;repositionEntityAfterLoad()Z"))
    private boolean cancelShouldSetPositionOnLoad(Entity entity)
    {
        return false;
    }
    
    @Inject(
            method = "load",
            at = @At(value = "INVOKE", shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/world/level/storage/ValueInput;)V")
    )
    private void onFromTag(ValueInput view, CallbackInfo ci)
    {
        if (this.repositionEntityAfterLoad())
        {
            this.reapplyPosition();
        }
        if (CarpetExtraSettings.reloadSuffocationFix && view.childrenList("CM_Box").isPresent())
        {
            Iterator<Double> boxTag = view.listOrEmpty("CM_Box", Codec.DOUBLE).stream().iterator();
            
            AABB box = new AABB(boxTag.next(), boxTag.next(),
                    boxTag.next(), boxTag.next(),
                    boxTag.next(), boxTag.next());
    
            double deltaX = ((box.minX + box.maxX) / 2.0D) - this.getX();
            double deltaY = box.minY - this.getY();
            double deltaZ = ((box.minZ + box.maxZ) / 2.0D) - this.getZ();
            
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