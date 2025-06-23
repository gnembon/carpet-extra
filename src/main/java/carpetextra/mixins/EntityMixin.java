package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Shadow public abstract Box getBoundingBox();
    
    //@Shadow protected abstract NbtList toNbtList(double... doubles_1);
    
    @Shadow protected abstract boolean shouldSetPositionOnLoad();
    
    @Shadow public abstract double getX();
    
    @Shadow public abstract double getY();
    
    @Shadow public abstract double getZ();
    
    @Shadow public abstract void setBoundingBox(Box box_1);

    @Shadow protected abstract void refreshPosition();

    @Unique
    protected NbtCompound newDoubleList(final double... values) {
        NbtList list = new NbtList();
        for (final double value : values) {
            list.add(NbtDouble.of(value));
        }
        NbtCompound result = new NbtCompound();
        result.put("list", list);
        return result;
    }

    @Inject(
            method = "writeData",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0,
                    target = "Lnet/minecraft/storage/WriteView;putDouble(Ljava/lang/String;D)V")
    )
    private void onToTag(WriteView view, CallbackInfo ci)
    {
        if (CarpetExtraSettings.reloadSuffocationFix)
        {
            Box box = this.getBoundingBox();
            System.out.println("Writing CM box");
            view.put("CM_Box", NbtCompound.CODEC, newDoubleList(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ));
        }
    }
    
    @Redirect(method = "readData", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;shouldSetPositionOnLoad()Z"))
    private boolean cancelShouldSetPositionOnLoad(Entity entity)
    {
        return false;
    }
    
    @Inject(
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
        if (CarpetExtraSettings.reloadSuffocationFix && view.getOptionalReadView("CM_Box").isPresent())
        {
            NbtList boxTag = view.read("CM_Box", NbtCompound.CODEC).orElse(new NbtCompound()).getListOrEmpty("list");
            if (boxTag.isEmpty()) {
                System.out.println("Invalid or outdated CM_Box");
                return; // nothing to do
            }
            System.out.println("Handling CM_Box");
            
            Box box = new Box(boxTag.getDouble(0).get(), boxTag.getDouble(1).get(),
                    boxTag.getDouble(2).get(), boxTag.getDouble(3).get(),
                    boxTag.getDouble(4).get(), boxTag.getDouble(5).get());
    
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