package carpetextra.utils;

import carpetextra.CarpetExtraServer;
import carpet.CarpetServer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import java.lang.System;

public class RulePrinter implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        CarpetExtraServer.noop();
        CarpetServer.onGameStarted();
        CarpetServer.settingsManager.printAllRulesToLog("extras");
        System.exit(0);
    }
}
