package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.helpers.DragonEggBedrockBreaking;
<<<<<<< HEAD
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
=======
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin
{
<<<<<<< HEAD
    @Inject(method = "applyBiomeDecoration", at = @At("HEAD"))
    private void onGenerateFeaturesStart(WorldGenLevel world, ChunkAccess chunk, StructureManager structureAccessor, CallbackInfo ci)
=======
    @Inject(method = "generateFeatures", at = @At("HEAD"))
    private void onGenerateFeaturesStart(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo ci)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        if (CarpetExtraSettings.dragonEggBedrockBreaking)
            DragonEggBedrockBreaking.fallInstantly = true;
    }
    
<<<<<<< HEAD
    @Inject(method = "applyBiomeDecoration", at = @At("TAIL"))
    private void onGenerateFeaturesEnd(WorldGenLevel world, ChunkAccess chunk, StructureManager structureAccessor, CallbackInfo ci)
=======
    @Inject(method = "generateFeatures", at = @At("TAIL"))
    private void onGenerateFeaturesEnd(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo ci)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        if (CarpetExtraSettings.dragonEggBedrockBreaking)
            DragonEggBedrockBreaking.fallInstantly = false;
    }
}
