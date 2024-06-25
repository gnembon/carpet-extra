package carpetextra.testbootstrap;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import carpet.CarpetServer;
import carpetextra.TestUtils;
import net.minecraft.test.TestFunctions;

public class TestBootstraper {
    static final String TEST_BASE_PACKAGE = "carpetextra.tests";
    static {
		registerExtensionForCommands();
		registerAllClasses();
	}
	
	static Stream<ClassInfo> allTestClasses() {
	    try {
            return ClassPath.from(TestBootstraper.class.getClassLoader())
                    .getAllClasses()
                    .stream()
                    .filter(clazz -> clazz.getPackageName().startsWith(TEST_BASE_PACKAGE) && clazz.isTopLevel());
        } catch (IOException e) {
            throw new UncheckedIOException("Exception while listing Carpet Extra gametests", e);
        }
	}
	
	private static void registerAllClasses() {
	    AtomicInteger count = new AtomicInteger();
        allTestClasses()
            .map(cls -> {
                try {
                    // cls.load() doesn't initialize it, making it not return annotations to the framework
                    return Class.forName(cls.getName());
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException("Couldn't initialize test class we know is there", e);
                }
            })
            .peek(__ -> count.setPlain(count.getPlain() + 1)) // technically shouldn't be used for this, but good enough
            .forEach(TestBootstraper::register);
        TestUtils.LOGGER.info("Registered " + count + " gametest classes for Carpet Extra");
	}
	
	static void register(Class<?> test) {
        // HACK: add our modid to gametest api first, or it'll crash
	    @SuppressWarnings("unchecked")
        class HackHolder {
	        static final HashMap<Class<?>, String> IDS;
	        static {
	            HashMap<Class<?>, String> ids;
                try {
                    Field f = Class.forName("net.fabricmc.fabric.impl.gametest.FabricGameTestModInitializer").getDeclaredField("GAME_TEST_IDS");
                    f.setAccessible(true);
                    ids = (HashMap<Class<?>, String>)f.get(null);
                } catch (ReflectiveOperationException e) {
                    TestUtils.LOGGER.warn("Hack to register gametest into fabric gametest failed, expect a crash!");
                    ids = null;
                }
                IDS = ids;
	        }
	    }

	    if (HackHolder.IDS != null) {
	        HackHolder.IDS.put(test, "carpet-extra");
	    }
	    
	    TestFunctions.register(test);
	}

	static void registerExtensionForCommands() {
	    CarpetServer.manageExtension(new TestsClassesCommandExtension());
	}
}
