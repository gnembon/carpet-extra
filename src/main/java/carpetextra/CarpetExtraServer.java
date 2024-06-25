package carpetextra;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpetextra.commands.PingCommand;
import carpetextra.dispenser.DispenserEvent;
import carpetextra.helpers.CustomSpawnLists;
import carpetextra.utils.CarpetExtraTranslations;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandRegistryAccess;
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
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            try {
                Class.forName("carpetextra.testbootstrap.TestBootstraper");
            } catch (ClassNotFoundException e) {
                // Class may not be available if in a different mod's dev env
                // However, we'd still like to crash if we can make sure we're in Carpet extra's, for debugging purposes
                String version = FabricLoader.getInstance()
                        .getModContainer("carpet-extra").orElseThrow()
                        .getMetadata().getVersion()
                        .getFriendlyString();
                if (version.equals("${version}")) {
                    throw new IllegalStateException("Couldn't bootstrap CarpetExtra tests!");
                }
            }
        }
    }

    @Override
    public void onGameStarted()
    {
        // let's /carpet handle our few simple settings
        CarpetServer.settingsManager.parseSettingsClass(CarpetExtraSettings.class);
        CustomSpawnLists.addExtraSpawnRules();
        DispenserEvent.init();
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess)
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
