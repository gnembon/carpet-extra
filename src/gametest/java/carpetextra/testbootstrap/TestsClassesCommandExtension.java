package carpetextra.testbootstrap;

import static carpetextra.testbootstrap.TestBootstraper.TEST_BASE_PACKAGE;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.command.CommandSource.suggestMatching;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import carpet.CarpetExtension;
import carpet.utils.Messenger;
import carpetextra.TestUtils;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.data.DataWriter;
import net.minecraft.data.dev.NbtProvider;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.test.TestFunctions;
import net.minecraft.util.WorldSavePath;

// to add a command to register test classes at runtime
public class TestsClassesCommandExtension implements CarpetExtension {
    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandBuildContext) {
        registerTestClassCommand(dispatcher);
    }

    public static void registerTestClassCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("testhelper")
                .then(literal("class")
                        .then(argument("class", word())
                        .suggests((c, b) -> suggestMatching(TestBootstraper.allTestClasses()
                                                .<String>mapMulti((clazz, stream) -> {
                                                    if (!TestFunctions.testClassExists(clazz.getSimpleName()))
                                                        stream.accept(clazz.getName());
                                                })
                                                .map(name -> name.substring(TEST_BASE_PACKAGE.length() + 1))
                                            , b))
                        .executes(c -> {
                            String className = getString(c, "class");
                            try {
                                Class<?> testClass = Class.forName(TEST_BASE_PACKAGE + '.' + className);
                                if (TestFunctions.testClassExists(testClass.getSimpleName())) {
                                    c.getSource().sendError(Messenger.c("r Reloading classes is not supported"));
                                    return 0;
                                }
                                TestBootstraper.register(testClass);
                                c.getSource().sendMessage(Messenger.c(" Added test class " + className));
                                return 1;
                            } catch (ClassNotFoundException e) {
                                c.getSource().sendError(Messenger.c("r Failed to load class " + className));
                                return 0;
                            }
                        }))
                )
                .then(literal("convertnbt")
                        .then(argument("structure", word())
                                .suggests((c, b) -> suggestMatching(listStructures(c), b))
                                .executes(c -> {
                                    String fileName = getString(c, "structure");
                                    Path from = structuresPath(c).resolve(fileName + ".nbt");
                                    Path to = Path.of("../src/gametest/resources/data/carpet-extra/gametest/structure");
                                    NbtProvider.convertNbtToSnbt(DataWriter.UNCACHED, from, fileName, to);
                                    c.getSource().sendMessage(Messenger.c(" Converted and moved structure " + fileName));
                                    return 0;
                                })
                        )
                );
        dispatcher.register(command);
    }
    
    private static Path structuresPath(CommandContext<ServerCommandSource> ctx) {
        return ctx.getSource().getServer().getSavePath(WorldSavePath.GENERATED).resolve("minecraft/structures");
    }
    
    private static Collection<String> listStructures(CommandContext<ServerCommandSource> ctx) {
        try (Stream<Path> paths = Files.list(structuresPath(ctx))) {
            return paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".nbt"))
                    .map(p -> p.substring(0, p.length() - 4))
                    .toList();
        } catch (IOException e) {
            TestUtils.LOGGER.info("Failed to list structures", e);
            // do nothing?
            return Stream.<String>empty().toList();
        }
    }
    
//    private static void reloadTestClass(Class<?> cls) {
//        String fabricBatchId = cls.getSimpleName().toLowerCase();
//        TestFunctions.getTestFunctions().removeIf(fn -> fn.templatePath().startsWith(fabricBatchId));
//        // we'd need to remove before and after batch handlers too
//        TestFunctions.register(cls);
//    }
    
    @Override
    public String version() {
        return "testcommand-adder";
    }
}
