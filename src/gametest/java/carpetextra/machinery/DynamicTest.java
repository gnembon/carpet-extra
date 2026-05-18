package carpetextra.machinery;

import java.util.Locale;
import java.util.function.Consumer;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.world.level.block.Rotation;

// slightly extended TestData
public record DynamicTest(
        String environment,
        String name,
        String structure,
        int maxTicks,
        int setupTicks,
        boolean required,
        Rotation rotation,
        boolean manualOnly,
        int maxAttempts,
        int requiredSuccesses,
        boolean skyAccess,
        int padding,
        Consumer<GameTestHelper> testFunction
    ) {
    
    public DynamicTest(String environment, String name, String structure, int maxTicks, int setupTicks, boolean required, Rotation rotation, Consumer<GameTestHelper> runner) {
        this(environment, name, structure, maxTicks, setupTicks, required, rotation, false, 1, 1, false, 0, runner);
    }

    public DynamicTest(String environment, String name, String structure, int maxTicks, int setupTicks, boolean required, Consumer<GameTestHelper> runner) {
        this(environment, name, structure, maxTicks, setupTicks, required, Rotation.NONE, runner);
    }

    // adapted from TestAnnotationLocator.TestMethod
    public FunctionGameTestInstance testInstance(Registry<TestEnvironmentDefinition<?>> envRegistry) {
        return new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, identifier()),
                testData(envRegistry)
        );
    }
    
    TestData<Holder<TestEnvironmentDefinition<?>>> testData(Registry<TestEnvironmentDefinition<?>> testEnvironmentDefinitionRegistry) {
        DynamicTest gameTest = this;
        Holder<TestEnvironmentDefinition<?>> testEnvironment = testEnvironmentDefinitionRegistry.getOrThrow(ResourceKey.create(Registries.TEST_ENVIRONMENT, Identifier.parse(gameTest.environment())));
        
        return new TestData<>(
                testEnvironment,
                Identifier.parse(gameTest.structure()),
                gameTest.maxTicks(),
                gameTest.setupTicks(),
                gameTest.required(),
                gameTest.rotation(),
                gameTest.manualOnly(),
                gameTest.maxAttempts(),
                gameTest.requiredSuccesses(),
                gameTest.skyAccess(),
                gameTest.padding()
        );
    }
    
    private static String camelToSnake(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
    }
    
    public Identifier identifier() {
        return Identifier.fromNamespaceAndPath("carpet-extra-gametest", camelToSnake(name));
    }
    
}
