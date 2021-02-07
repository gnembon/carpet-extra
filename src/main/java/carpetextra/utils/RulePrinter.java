package carpetextra.utils;

import carpetextra.CarpetExtraServer;
import carpet.CarpetServer;
import carpet.utils.CarpetRulePrinter;
import net.fabricmc.api.DedicatedServerModInitializer;
import java.lang.System;

public class RulePrinter implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        System.setOut(CarpetRulePrinter.OLD_OUT);
        CarpetExtraServer.noop();
        CarpetServer.onGameStarted();
        CarpetServer.settingsManager.printAllRulesToLog("extras");
        System.exit(0);
    }
}
