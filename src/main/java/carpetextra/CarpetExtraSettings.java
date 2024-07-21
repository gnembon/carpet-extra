package carpetextra;

import carpet.api.settings.Rule;
import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import carpet.api.settings.Validators;
import org.jetbrains.annotations.Nullable;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.api.settings.RuleCategory.*;

/**
 * Here is your example Settings class you can plug to use carpetmod /carpet settings command
 */
public class CarpetExtraSettings
{
    public enum ComparatorOptions {
        VANILLA,
        BEHIND,
        LENIENT,
        EXTENDED;
    }

    public static final String EXTRA = "extras";

    public static class validatorScaffoldingDistance extends Validator<Integer> {
        @Override
        public Integer validate(ServerCommandSource source, CarpetRule<Integer> currentRule, Integer newValue, String string) {
            return newValue >= 0 && newValue <= 7 ? newValue : null;
        }

        @Override
        public String description() { return "You must choose a value from 0 to 7";}
    }
    @Rule(
            options = { "2", "3", "5", "7" },
            categories = {FEATURE, EXTRA},
            strict = false,
            validators = validatorScaffoldingDistance.class
    )
    public static int scaffoldingDistance = 7;

    @Rule(
            categories = {EXTRA, FEATURE}
    )
    public static boolean pistonRedirectsRedstone = false;

    @Rule(
            categories = {CREATIVE, EXTRA, DISPENSER}
    )
    public static boolean autoCraftingDropper = false;

    @Rule(categories = {CREATIVE, EXTRA, DISPENSER})
    public static boolean dispenserPlacesBlocks = false;

    @Rule(
            categories = {EXTRA, FEATURE}
    )
    public static boolean variableWoodDelays = false;


    @Rule(
        categories = {FEATURE,EXTRA,EXPERIMENTAL}
    )
    public static boolean comparatorReadsClock = false;

    @Rule(
            categories = {FEATURE,EXTRA,EXPERIMENTAL}
    )
    public static ComparatorOptions comparatorBetterItemFrames = ComparatorOptions.VANILLA;

    @Rule(
        categories = {BUGFIX, FEATURE, EXTRA, EXPERIMENTAL}
    )
    public static boolean hopperMinecartItemTransfer = false;
    
    @Rule(categories = {COMMAND, EXTRA})
    public static boolean commandPing = true;

    @Rule(
            categories = {EXPERIMENTAL, FEATURE, EXTRA, DISPENSER}
    )
    public static boolean dispensersFillMinecarts = false;

    @Rule(
            categories = {EXTRA, FEATURE}
    )
    public static boolean clericsFarmWarts = false;
    
    @Rule(
            categories = {EXTRA, EXPERIMENTAL, FEATURE}
    )
    public static boolean renewableIce = false;

    @Rule(
            options = {"off", "V2", "V3"},
            categories = {EXTRA, SURVIVAL}
    )
    public static String accurateBlockPlacement = "off";

    @Rule(
            categories = {EXTRA, EXPERIMENTAL, FEATURE, DISPENSER}
    )
    public static boolean dispensersToggleThings = false;

    @Rule(categories = {EXTRA, FEATURE, DISPENSER})
    public static boolean dispensersTillSoil = false;

    @Rule(
            categories = {EXTRA, EXPERIMENTAL, FEATURE, DISPENSER}
    )
    public static boolean dispensersFeedAnimals = false;
    
    @Rule(categories = {EXTRA, CREATIVE, EXPERIMENTAL})
    public static boolean disablePlayerCollision = false;
    
    @Rule(
            categories = {EXTRA, EXPERIMENTAL}
    )
    public static boolean doubleRetraction = false;
    
    @Rule(
            categories = {EXTRA, EXPERIMENTAL}
    )
    public static boolean blockStateSyncing = false;
    
    @Rule(categories = {EXTRA, EXPERIMENTAL, FEATURE})
    public static boolean renewableSand = false;


    @Rule(categories = {EXTRA, EXPERIMENTAL})
    public static boolean dragonEggBedrockBreaking = false;
    
    @Rule(
            categories = {EXTRA, EXPERIMENTAL}
    )
    public static boolean renewableNetherrack = false;
    
    @Rule(
            categories = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean chickenShearing = false;
    
    @Rule(
            categories = {EXTRA, FEATURE, EXPERIMENTAL}
    )
    public static boolean mobInFireConvertsSandToSoulsand = false;
    
    @Rule(
            categories = {EXTRA, FEATURE, EXPERIMENTAL}
    )
    public static boolean flowerPotChunkLoading = false;
    
    @Rule(
            categories = {EXTRA, BUGFIX, EXPERIMENTAL}
    )
    public static boolean reloadSuffocationFix = false;
    
    @Rule(
            categories = {EXPERIMENTAL, EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersMilkAnimals = false;
    
    @Rule(
            categories = {EXTRA, BUGFIX, EXPERIMENTAL}
    )
    public static boolean repeaterPriorityFix = false;
    
    @Rule(categories = {FEATURE, EXTRA})
    public static boolean straySpawningInIgloos = false;
    
    @Rule(categories = {FEATURE, EXTRA})
    public static boolean renewableWitherSkeletons = false;
    
    @Rule(categories = {FEATURE, EXTRA})
    public static boolean creeperSpawningInJungleTemples = false;
    
    @Rule(
            categories = {EXPERIMENTAL, EXTRA}
    )
    public static boolean y0DragonEggBedrockBreaking = false;
    
    public static class ValidateSpiderJokeyDropChance extends Validator<Integer>
    {
        @Override
        public Integer validate(ServerCommandSource source, CarpetRule<Integer> currentRule, Integer newValue, String string)
        {
            return newValue >= 0 && newValue <= 100 ? newValue : null;
        }
    
        @Override
        public String description() { return "You must choose a value from 0 to 100";}
    }
    
    @Rule(
            options = {"0", "50", "100"},
            categories = {EXTRA, FEATURE},
            strict = false,
            validators = ValidateSpiderJokeyDropChance.class
    )
    public static int spiderJockeysDropGapples = 0;
    
    @Rule(
            categories = {EXTRA, EXPERIMENTAL, DISPENSER}
    )
    public static boolean renewableEndstone = false;

    @Rule(
            options = {"64"},
            categories = {FEATURE, EXTRA},
            strict = false
    )
    public static int maxSpongeSuck = 64;

    @Rule(
            options = {"7"},
            categories = {FEATURE, EXTRA},
            strict = false
    )
    public static int maxSpongeRange = 7;

    @Rule(
            categories = {FEATURE, EXTRA, EXPERIMENTAL}
    )
    public static boolean emptyShulkerBoxStackAlways = false;

    @Rule(
            categories = {FEATURE, EXTRA}
    )
    public static boolean enderPearlChunkLoading = false;

    @Rule(
            categories = {FEATURE, EXTRA, SURVIVAL}
    )
    public static boolean betterBonemeal = false;

    @Rule(
            categories = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersCarvePumpkins = false;
    
    @Rule(
            categories = {EXTRA, FEATURE, SURVIVAL}
    )
    public static boolean blazeMeal = false;

    @Rule(
            categories = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersStripBlocks = false;

    @Rule(
            categories = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersUseCauldrons = false;

    @Rule(
            categories = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersPotPlants = false;

    @Rule(
            categories = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersPlaceBoatsOnIce = false;

    @Rule(
            categories = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean fallingBlockDispensers = false;

    @Rule(
            options = {"5", "10", "20"},
            strict = false,
            categories = {EXTRA, FEATURE},
            validators = Validators.NonNegativeNumber.class
    )
    public static int xpPerSculkCatalyst = 5;
}
