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
        CarpetExtraServer.loadExtension();
        CarpetServer.onGameStarted();
        CarpetServer.settingsManager.dumpAllRulesToStream(System.out, "extras");
        System.exit(0);
    }
}
