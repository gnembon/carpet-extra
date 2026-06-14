package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import java.util.Iterator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueOutput.TypedOutputList;
import net.minecraft.world.phys.AABB;
=======
import net.minecraft.entity.Entity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.storage.WriteView.ListAppender;
import net.minecraft.util.math.Box;

import java.util.Iterator;

>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
<<<<<<< HEAD
    @Shadow public abstract AABB getBoundingBox();
    
    //@Shadow protected abstract NbtList toNbtList(double... doubles_1);
    
    @Shadow protected abstract boolean repositionEntityAfterLoad();
=======
    @Shadow public abstract Box getBoundingBox();
    
    //@Shadow protected abstract NbtList toNbtList(double... doubles_1);
    
    @Shadow protected abstract boolean shouldSetPositionOnLoad();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    
    @Shadow public abstract double getX();
    
    @Shadow public abstract double getY();
    
    @Shadow public abstract double getZ();
    
<<<<<<< HEAD
    @Shadow public abstract void setBoundingBox(AABB box_1);

    @Shadow protected abstract void reapplyPosition();

    @Unique
    protected void fillDoubleAppender(TypedOutputList<Double> list, double... values) {
=======
    @Shadow public abstract void setBoundingBox(Box box_1);

    @Shadow protected abstract void refreshPosition();

    @Unique
    protected void fillDoubleAppender(ListAppender<Double> list, double... values) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        for (final double value : values) {
            list.add(value);
        }
    }

    @Inject(
<<<<<<< HEAD
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
=======
            method = "writeData",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0,
                    target = "Lnet/minecraft/storage/WriteView;putDouble(Ljava/lang/String;D)V")
    )
    private void onToTag(WriteView view, CallbackInfo ci)
    {
        if (CarpetExtraSettings.reloadSuffocationFix)
        {
            Box box = this.getBoundingBox();
            fillDoubleAppender(view.getListAppender("CM_Box", Codec.DOUBLE), box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
        }
    }
    
    @Redirect(method = "readData", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;shouldSetPositionOnLoad()Z"))
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    private boolean cancelShouldSetPositionOnLoad(Entity entity)
    {
        return false;
    }
    
    @Inject(
<<<<<<< HEAD
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
=======
            method = "readData",
            at = @At(value = "INVOKE", shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/entity/Entity;readCustomData(Lnet/minecraft/storage/ReadView;)V")
    )
    private void onFromTag(ReadView view, CallbackInfo ci)
    {
        if (this.shouldSetPositionOnLoad())
        {
            this.refreshPosition();
        }
        if (CarpetExtraSettings.reloadSuffocationFix && view.getOptionalListReadView("CM_Box").isPresent())
        {
            Iterator<Double> boxTag = view.getTypedListView("CM_Box", Codec.DOUBLE).stream().iterator();
            
            Box box = new Box(boxTag.next(), boxTag.next(),
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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