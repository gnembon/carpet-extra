package carpetextra;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpetextra.commands.PingCommand;
import carpetextra.dispenser.DispenserEvent;
import carpetextra.helpers.CustomSpawnLists;
import carpetextra.utils.CarpetExtraTranslations;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
<<<<<<< HEAD
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
=======
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;

>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
        DispenserEvent.init();
    }

    @Override
<<<<<<< HEAD
    public void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess)
=======
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
