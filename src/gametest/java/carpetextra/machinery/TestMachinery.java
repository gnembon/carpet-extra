package carpetextra.machinery;

import java.nio.file.Path;

import net.fabricmc.api.ModInitializer;
import net.minecraft.gametest.framework.GameTestMainUtil;
import net.minecraft.gametest.framework.StructureUtils;

public class TestMachinery implements ModInitializer {
    @Override
    public void onInitialize() {
        StructureUtils.testStructuresSourceDir = Path.of("../src/gametest/resources/data/carpet-extra/gametest/structure");
        TestEnvDefinitions.runRegistration();
        TestProviderRunner.onInitialize();
    }
}
