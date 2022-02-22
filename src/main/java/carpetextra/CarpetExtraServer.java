package carpetextra;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpetextra.commands.PingCommand;
import carpetextra.helpers.CustomSpawnLists;
import carpetextra.utils.CarpetExtraTranslations;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Map;

public class CarpetExtraServer implements CarpetExtension, ModInitializer
{
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
        CustomSpawnLists.addExtraSpawnRules();
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
}
