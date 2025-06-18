package carpetextra.machinery;

import java.util.Locale;
import java.util.function.Consumer;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.test.FunctionTestInstance;
import net.minecraft.test.TestContext;
import net.minecraft.test.TestData;
import net.minecraft.test.TestEnvironmentDefinition;
import net.minecraft.test.TestInstance;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;

// slightly extended TestData
public record DynamicTest(
        String environment,
        String name,
        String structure,
        int maxTicks,
        int setupTicks,
        boolean required,
        BlockRotation rotation,
        boolean manualOnly,
        int maxAttempts,
        int requiredSuccesses,
        boolean skyAccess,
        Consumer<TestContext> testFunction
    ) {
    
    public DynamicTest(String environment, String name, String structure, int maxTicks, int setupTicks, boolean required, BlockRotation rotation, Consumer<TestContext> runner) {
        this(environment, name, structure, maxTicks, setupTicks, required, rotation, false, 1, 1, false, runner);
    }

    public DynamicTest(String environment, String name, String structure, int maxTicks, int setupTicks, boolean required, Consumer<TestContext> runner) {
        this(environment, name, structure, maxTicks, setupTicks, required, BlockRotation.NONE, runner);
    }

    // adapted from TestAnnotationLocator.TestMethod
    public TestInstance testInstance(Registry<TestEnvironmentDefinition> envRegistry) {
        return new FunctionTestInstance(
                RegistryKey.of(RegistryKeys.TEST_FUNCTION, identifier()),
                testData(envRegistry)
        );
    }
    
    TestData<RegistryEntry<TestEnvironmentDefinition>> testData(Registry<TestEnvironmentDefinition> testEnvironmentDefinitionRegistry) {
        DynamicTest gameTest = this;
        RegistryEntry<TestEnvironmentDefinition> testEnvironment = testEnvironmentDefinitionRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TEST_ENVIRONMENT, Identifier.of(gameTest.environment())));
        
        return new TestData<>(
                testEnvironment,
                Identifier.of(gameTest.structure()),
                gameTest.maxTicks(),
                gameTest.setupTicks(),
                gameTest.required(),
                gameTest.rotation(),
                gameTest.manualOnly(),
                gameTest.maxAttempts(),
                gameTest.requiredSuccesses(),
                gameTest.skyAccess()
        );
    }
    
    private static String camelToSnake(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
    }
    
    public Identifier identifier() {
        return Identifier.of("carpet-extra-gametest", camelToSnake(name));
    }
    
}
