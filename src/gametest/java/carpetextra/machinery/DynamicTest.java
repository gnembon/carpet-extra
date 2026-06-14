package carpetextra.machinery;

import java.util.Locale;
import java.util.function.Consumer;
<<<<<<< HEAD
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
=======

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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

// slightly extended TestData
public record DynamicTest(
        String environment,
        String name,
        String structure,
        int maxTicks,
        int setupTicks,
        boolean required,
<<<<<<< HEAD
        Rotation rotation,
=======
        BlockRotation rotation,
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        boolean manualOnly,
        int maxAttempts,
        int requiredSuccesses,
        boolean skyAccess,
<<<<<<< HEAD
        Consumer<GameTestHelper> testFunction
    ) {
    
    public DynamicTest(String environment, String name, String structure, int maxTicks, int setupTicks, boolean required, Rotation rotation, Consumer<GameTestHelper> runner) {
        this(environment, name, structure, maxTicks, setupTicks, required, rotation, false, 1, 1, false, runner);
    }

    public DynamicTest(String environment, String name, String structure, int maxTicks, int setupTicks, boolean required, Consumer<GameTestHelper> runner) {
        this(environment, name, structure, maxTicks, setupTicks, required, Rotation.NONE, runner);
    }

    // adapted from TestAnnotationLocator.TestMethod
    public GameTestInstance testInstance(Registry<TestEnvironmentDefinition> envRegistry) {
        return new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, identifier()),
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                testData(envRegistry)
        );
    }
    
<<<<<<< HEAD
    TestData<Holder<TestEnvironmentDefinition>> testData(Registry<TestEnvironmentDefinition> testEnvironmentDefinitionRegistry) {
        DynamicTest gameTest = this;
        Holder<TestEnvironmentDefinition> testEnvironment = testEnvironmentDefinitionRegistry.getOrThrow(ResourceKey.create(Registries.TEST_ENVIRONMENT, ResourceLocation.parse(gameTest.environment())));
        
        return new TestData<>(
                testEnvironment,
                ResourceLocation.parse(gameTest.structure()),
=======
    TestData<RegistryEntry<TestEnvironmentDefinition>> testData(Registry<TestEnvironmentDefinition> testEnvironmentDefinitionRegistry) {
        DynamicTest gameTest = this;
        RegistryEntry<TestEnvironmentDefinition> testEnvironment = testEnvironmentDefinitionRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TEST_ENVIRONMENT, Identifier.of(gameTest.environment())));
        
        return new TestData<>(
                testEnvironment,
                Identifier.of(gameTest.structure()),
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
    
<<<<<<< HEAD
    public ResourceLocation identifier() {
        return ResourceLocation.fromNamespaceAndPath("carpet-extra-gametest", camelToSnake(name));
=======
    public Identifier identifier() {
        return Identifier.of("carpet-extra-gametest", camelToSnake(name));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
    
}
