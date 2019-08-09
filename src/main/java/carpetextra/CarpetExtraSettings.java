package carpetextra;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.*;

/**
 * Here is your example Settings class you can plug to use carpetmod /carpet settings command
 */
public class CarpetExtraSettings
{
    public static final String EXTRA = "extras";

    @Rule(desc = "Auto-crafting table", category = {CREATIVE, EXTRA})
    public static boolean autoCraftingTable = false;


    @Rule(desc="Dispensers can place blocks", category = {CREATIVE, EXTRA})
    public static boolean dispenserPlacesBlocks = false;


    @Rule(
        desc = "Allows Comparators to read the daytime instead of the rotation of clocks in item frames",
        category = {FEATURE,EXTRA,EXPERIMENTAL}
    )
    public static boolean comparatorReadsClock = false;

    @Rule(
        desc = "Makes Hopper Minecarts have an 8gt cooldown like hoppers.",
        category = {BUGFIX,FEATURE,EXTRA,EXPERIMENTAL}
    )
    public static boolean hopperMinecart8gtCooldown = false;

    @Rule(
        desc = "Allows Hopper Minecarts to transfer items out.",
        category = {BUGFIX,FEATURE,EXTRA,EXPERIMENTAL}
    )
    public static boolean hopperMinecartItemTransfer= false;
    
    @Rule(desc = "Enables /ping for players to get their ping", category = {COMMAND, EXTRA})
    public static boolean commandPing = true;
    
    @Rule(
            desc = "Water bottles in dispensers fill with water when dispensed with water in front.",
            category = {EXPERIMENTAL, FEATURE, EXTRA}
    )
    public static boolean dispensersFillBottles;
    
    @Rule(
            desc = "Minecarts can be filled with hoppers, chests, tnt and furnace.",
            category = {EXPERIMENTAL, FEATURE, EXTRA}
    )
    public static boolean dispensersFillMinecarts = false;
    
}
