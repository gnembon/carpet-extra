package carpetextra.machinery;

import java.nio.file.Path;

import org.spongepowered.asm.mixin.MixinEnvironment;

import net.fabricmc.api.ModInitializer;
import net.minecraft.test.TestInstanceUtil;

public class TestMachinery implements ModInitializer {
    @Override
    public void onInitialize() {
        TestInstanceUtil.structureSnbtPath = Path.of("../src/gametest/resources/data/carpet-extra/gametest/structure");
        TestEnvDefinitions.runRegistration();
        TestProviderRunner.onInitialize();
        
        // Force all mixin classes to be verified
        MixinEnvironment.getCurrentEnvironment().audit();
    }
}
