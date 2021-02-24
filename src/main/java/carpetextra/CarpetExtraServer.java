package carpetextra;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.logging.Logger;
import carpetextra.commands.PingCommand;
import carpetextra.utils.CarpetExtraTranslations;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;

import static carpet.logging.LoggerRegistry.registerLogger;

public class CarpetExtraServer implements CarpetExtension
{

    public static boolean __noteBlockChunkLoader;
    public static boolean __pistonHeadChunkLoader;

    @Override
    public String version()
    {
        return "carpet-extra";
    }

    public static void noop() { }

    static
    {
        CarpetServer.manageExtension(new CarpetExtraServer());
        // temporary until CM proper runs tiny bit later
        //CarpetServer.settingsManager.parseSettingsClass(CarpetExtraSettings.class);
    }

    @Override
    public void onGameStarted()
    {
        // let's /carpet handle our few simple settings
        CarpetServer.settingsManager.parseSettingsClass(CarpetExtraSettings.class);

        // set-up a snooper to observe how rules are changing in carpet
        CarpetServer.settingsManager.addRuleObserver( (serverCommandSource, currentRuleState, originalUserTest) ->
        {
            // here we will be snooping for command changes
        });
    }

    @Override
    public void onServerLoaded(MinecraftServer server)
    {
        // reloading of /carpet settings is handled by carpet
        // reloading of own settings is handled as an extension, since we claim own settings manager
        // in case something else falls into
    }

    @Override
    public void onTick(MinecraftServer server)
    {
        // maybe, maybe
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        // here goes extra stuff
        PingCommand.register(dispatcher);
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayerEntity player)
    {
         // will need that for client features
    }

    @Override
    public void onPlayerLoggedOut(ServerPlayerEntity player)
    {
        // will need that for client features
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
