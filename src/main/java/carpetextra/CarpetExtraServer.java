package carpetextra;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.logging.Logger;
import carpetextra.commands.PingCommand;
import carpetextra.utils.CarpetExtraTranslations;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Map;

import static carpet.logging.LoggerRegistry.registerLogger;

public class CarpetExtraServer implements CarpetExtension, ModInitializer
{

    public static boolean __noteBlockChunkLoader;
    public static boolean __pistonHeadChunkLoader;

    @Override
    public String version()
    {
        return "carpet-extra";
    }

    public static void loadExtension()
    {
        CarpetServer.manageExtension(new CarpetExtraServer());
    }

    @Override
    public void onInitialize()
    {
        CarpetExtraServer.loadExtension();
    }

    @Override
    public void onGameStarted()
    {
        // let's /carpet handle our few simple settings
        CarpetServer.settingsManager.parseSettingsClass(CarpetExtraSettings.class);
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        // here goes extra stuff
        PingCommand.register(dispatcher);
    }

    @Override
    public Map<String, String> canHasTranslations(String lang)
    {
        return CarpetExtraTranslations.getTranslationFromResourcePath(lang);
    }

    @Override
    public void registerLoggers() {

        registerLogger("noteBlockChunkLoader", stardardLogger("noteBlockChunkLoader","dynamic", new String[]{"dynamic", "overworld", "nether","end"}));
        registerLogger("pistonHeadChunkLoader", stardardLogger("pistonHeadChunkLoader","dynamic", new String[]{"dynamic", "overworld", "nether","end"}));
    }

    static Logger stardardLogger(String logName, String def, String [] options)
    {
        try
        {
            return new Logger(CarpetExtraServer.class.getField("__"+logName), logName, def, options);
        }
        catch (NoSuchFieldException e)
        {
            throw new RuntimeException("Failed to create logger "+logName);
        }
    }
}
