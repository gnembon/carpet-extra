package carpetextra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import carpetextra.machinery.TestProviderRunner;
import net.fabricmc.fabric.impl.gametest.FabricGameTestModInitializer;
<<<<<<< HEAD
import net.minecraft.core.Registry;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
=======
import net.minecraft.registry.Registry;
import net.minecraft.test.TestEnvironmentDefinition;
import net.minecraft.test.TestInstance;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

@Mixin(value = FabricGameTestModInitializer.class, remap = false)
class FabricGametestMixin {
    @Inject(method = "registerDynamicEntries", at = @At("TAIL"))
    private static void addOursToo(CallbackInfo ci, 
<<<<<<< HEAD
            @Local(name="testInstances") Registry<GameTestInstance> testInstances,
=======
            @Local(name="testInstances") Registry<TestInstance> testInstances,
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            @Local(name="testEnvironmentDefinitionRegistry") Registry<TestEnvironmentDefinition> envRegistry) {
        TestProviderRunner.registerDynamicEntries(testInstances, envRegistry);
    }
}
