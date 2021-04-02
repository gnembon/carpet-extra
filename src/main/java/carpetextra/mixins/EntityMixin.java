package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract Box getBoundingBox();

    @Shadow
    protected abstract NbtList toListTag(double... doubles_1);

    @Shadow
    protected abstract boolean shouldSetPositionOnLoad();

    @Shadow
    public abstract double getX();

    @Shadow
    public abstract double getY();

    @Shadow
    public abstract double getZ();

    @Shadow
    public abstract void setBoundingBox(Box box_1);

    @Shadow
    protected abstract void refreshPosition();

    @Inject(
            method = "writeNbt",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0,
                    target = "Lnet/minecraft/nbt/NbtCompound;put(Ljava/lang/String;Lnet/minecraft/nbt/NbtElement;)Lnet/minecraft/nbt/NbtElement;")
    )
    private void onToTag(NbtCompound compoundTag_1, CallbackInfoReturnable<NbtCompound> cir) {
        if (CarpetExtraSettings.reloadSuffocationFix) {
            Box box = this.getBoundingBox();
            compoundTag_1.put("CM_Box", this.toListTag(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ));
        }
    }

    @Redirect(method = "readNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;shouldSetPositionOnLoad()Z"))
    private boolean cancelShouldSetPositionOnLoad(Entity entity) {
        return false;
    }

    @Inject(
            method = "readNbt",
            at = @At(value = "INVOKE", shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/entity/Entity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V")
    )
    private void onFromTag(NbtCompound compoundTag_1, CallbackInfo ci) {
        if (this.shouldSetPositionOnLoad()) {
            this.refreshPosition();
        }

        if (CarpetExtraSettings.reloadSuffocationFix && compoundTag_1.contains("CM_Box", 9)) {
            NbtList box_tag = compoundTag_1.getList("CM_Box", 6);

            Box box = new Box(box_tag.getDouble(0), box_tag.getDouble(1),
                    box_tag.getDouble(2), box_tag.getDouble(3),
                    box_tag.getDouble(4), box_tag.getDouble(5));

            double deltaX = ((box.minX + box.maxX) / 2.0D) - this.getX();
            double deltaY = box.minY - this.getY();
            double deltaZ = ((box.minZ + box.maxZ) / 2.0D) - this.getZ();

            // Credits: MrGrim (MUP) -> Sanity check.
            // If the position and BoundingBox center point are > 0.1 blocks apart then do not restore the BoundingBox. In vanilla
            // this should never happen, but mods might not be aware that the BoundingBox is stored and that the entity
            // position will be reset to it.
            if (((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ)) < 0.01D) {
                this.setBoundingBox(box);
            }
        }
    }

}