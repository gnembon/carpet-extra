package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.world.World;
import carpetextra.CarpetExtraSettings;
import carpetextra.utils.ChunkUtils;

@Mixin(ThrownEntity.class)
public abstract class ThrownEntityMixin extends Entity
{
    private ThrownEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Inject(method = "tick()V", at = @At(value = "HEAD"))
    private void chunkLoadNextChunk(CallbackInfo ci)
    {
        if (CarpetExtraSettings.enderPearlChunkLoading &&
            ((Object) this) instanceof EnderPearlEntity)
        {
            ChunkUtils.addCustomChunkTicket(this, ChunkUtils.ENDER_PEARL_TICKET);
        }
    }
}
