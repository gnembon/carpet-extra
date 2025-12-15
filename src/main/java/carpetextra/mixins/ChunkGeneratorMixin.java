package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.helpers.DragonEggBedrockBreaking;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin
{
    @Inject(method = "applyBiomeDecoration", at = @At("HEAD"))
    private void onGenerateFeaturesStart(WorldGenLevel world, ChunkAccess chunk, StructureManager structureAccessor, CallbackInfo ci)
    {
        if (CarpetExtraSettings.dragonEggBedrockBreaking)
            DragonEggBedrockBreaking.fallInstantly = true;
    }
    
    @Inject(method = "applyBiomeDecoration", at = @At("TAIL"))
    private void onGenerateFeaturesEnd(WorldGenLevel world, ChunkAccess chunk, StructureManager structureAccessor, CallbackInfo ci)
    {
        if (CarpetExtraSettings.dragonEggBedrockBreaking)
            DragonEggBedrockBreaking.fallInstantly = false;
    }
}
