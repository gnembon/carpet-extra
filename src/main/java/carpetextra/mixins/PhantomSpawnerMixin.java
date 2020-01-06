package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
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
        if (CarpetExtraSettings.disablePhantomSpawning)
            cir.setReturnValue(0);
    }
}
