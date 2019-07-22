package carpetextra;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.CREATIVE;

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

}
