package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.gen.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin
{
    @Inject(method = "spawn", at = @At("HEAD"), cancellable = true)
    private void onSpawn(ServerWorld serverWorld, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir)
    {
        // Credits: Skyrising (Quickcarpet)
        if (CarpetExtraSettings.phantomsRespectMobcap && spawnMonsters)
        {
            EntityCategory category = EntityType.PHANTOM.getCategory();
            int mobs = serverWorld.getMobCountsByCategory().getOrDefault(category, 0);
            int chunks = ((ServerChunkManagerAccessor) serverWorld.getChunkManager()).getTicketManager().getLevelCount();
            int max = chunks * category.getSpawnCap() / (17 * 17);
            if (mobs > max)
            {
                cir.setReturnValue(0);
                cir.cancel();
            }
        }
        
        if (CarpetExtraSettings.disablePhantomSpawning)
            cir.setReturnValue(0);
    }
}
