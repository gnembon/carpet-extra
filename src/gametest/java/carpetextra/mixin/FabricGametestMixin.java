package carpetextra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import carpetextra.machinery.TestProviderRunner;
import net.fabricmc.fabric.impl.gametest.FabricGameTestModInitializer;
import net.minecraft.registry.Registry;
import net.minecraft.test.TestEnvironmentDefinition;
import net.minecraft.test.TestInstance;

@Mixin(value = FabricGameTestModInitializer.class, remap = false)
class FabricGametestMixin {
    @Inject(method = "registerDynamicEntries", at = @At("TAIL"))
    private static void addOursToo(CallbackInfo ci, 
            @Local(name="testInstances") Registry<TestInstance> testInstances,
            @Local(name="testEnvironmentDefinitionRegistry") Registry<TestEnvironmentDefinition> envRegistry) {
        TestProviderRunner.registerDynamicEntries(testInstances, envRegistry);
    }
}
