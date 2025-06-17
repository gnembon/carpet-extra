package carpetextra.machinery;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.fabricmc.fabric.api.gametest.v1.CustomTestMethodInvoker;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.test.TestEnvironmentDefinition;
import net.minecraft.test.TestInstance;

// note: adapted from Fabric API implementation
public class TestProviderRunner {
    private static final List<DynamicTest> generated = new ArrayList<>();
    
    private static void createMethods(Object instance, Class<?> testClass) {
        for (Method m : testClass.getMethods()) {
            if (m.getDeclaringClass() == testClass && m.isAnnotationPresent(TestProvider.class)) {
                try {
                    @SuppressWarnings("unchecked")
                    var ret = (Collection<DynamicTest>) m.invoke(instance);
                    generated.addAll(ret);
                } catch (ReflectiveOperationException e) {
                    throw new IllegalStateException("Exception while creating tests", e);
                }
                
            }
        }
    }

    // adapted from FabricGameTestModInitializer
    public static void onInitialize() {
        List<EntrypointContainer<Object>> entrypoints = FabricLoader.getInstance()
                .getEntrypointContainers("fabric-gametest", Object.class);
        
        for (EntrypointContainer<Object> entrypoint : entrypoints) {
            Object o = entrypoint.getEntrypoint();
            if (o instanceof CustomTestMethodInvoker) throw new UnsupportedOperationException("" + o.getClass());
            createMethods(o, o.getClass());
        }
        
        for (DynamicTest test : generated) {
            Registry.register(Registries.TEST_FUNCTION, test.identifier(), test.testFunction());
        }
        
    }
    
    public static void registerDynamicEntries(Registry<TestInstance> testInstances, Registry<TestEnvironmentDefinition> envRegistry) {
        for (DynamicTest test : generated) {
            TestInstance testInstance = test.testInstance(envRegistry);
            Registry.register(testInstances, test.identifier(), testInstance);
        }
    }
}
