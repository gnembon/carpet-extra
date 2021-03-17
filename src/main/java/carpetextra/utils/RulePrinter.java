package carpetextra.utils;

import carpetextra.CarpetExtraServer;
import carpet.CarpetServer;
import carpet.utils.CarpetRulePrinter;
import net.fabricmc.api.ModInitializer;
import java.lang.System;

public class RulePrinter implements ModInitializer {
    @Override
    public void onInitialize() {
        System.setOut(CarpetRulePrinter.OLD_OUT);
        CarpetServer.manageExtension(new CarpetExtraServer());
        CarpetServer.onGameStarted();
        CarpetServer.settingsManager.printAllRulesToLog("extras");
        System.exit(0);
    }
}
