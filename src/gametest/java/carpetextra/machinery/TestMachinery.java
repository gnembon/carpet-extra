package carpetextra.machinery;

import java.nio.file.Path;

import net.fabricmc.api.ModInitializer;
import net.minecraft.test.TestInstanceUtil;

public class TestMachinery implements ModInitializer {
    @Override
    public void onInitialize() {
        TestInstanceUtil.testStructuresDirectoryName = Path.of("../src/gametest/resources/data/carpet-extra/gametest/structure");
        TestEnvDefinitions.runRegistration();
        TestProviderRunner.onInitialize();
    }
}
