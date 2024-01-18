package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.ChunkUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntity_chunkLoadingMixin extends Entity {

    public AbstractMinecartEntity_chunkLoadingMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "moveOnRail(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;applySlowdown()V"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;setPosition(DDD)V"
            )
    )
    private void chunkLoadNextChunk(CallbackInfo ci) {
        if (CarpetExtraSettings.minecartChunkLoading) {
            ChunkUtils.addCustomChunkTicket(this, ChunkUtils.MINECART_TICKET);
        }
    }
}
