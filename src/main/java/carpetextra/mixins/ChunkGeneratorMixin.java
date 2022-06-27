package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.helpers.DragonEggBedrockBreaking;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin
{
    @Inject(method = "generateFeatures", at = @At("HEAD"))
    private void onGenerateFeaturesStart(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo ci)
    {
        if (CarpetExtraSettings.dragonEggBedrockBreaking)
            DragonEggBedrockBreaking.fallInstantly = true;
    }
    
    @Inject(method = "generateFeatures", at = @At("TAIL"))
    private void onGenerateFeaturesEnd(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo ci)
    {
        if (CarpetExtraSettings.dragonEggBedrockBreaking)
            DragonEggBedrockBreaking.fallInstantly = false;
    }
}
