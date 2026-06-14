package carpetextra.machinery;

import java.nio.file.Path;

import net.fabricmc.api.ModInitializer;
<<<<<<< HEAD
import net.minecraft.gametest.framework.StructureUtils;
=======
import net.minecraft.test.TestInstanceUtil;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

public class TestMachinery implements ModInitializer {
    @Override
    public void onInitialize() {
<<<<<<< HEAD
        StructureUtils.testStructuresDir = Path.of("../src/gametest/resources/data/carpet-extra/gametest/structure");
=======
        TestInstanceUtil.testStructuresDirectoryName = Path.of("../src/gametest/resources/data/carpet-extra/gametest/structure");
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        TestEnvDefinitions.runRegistration();
        TestProviderRunner.onInitialize();
    }
}
