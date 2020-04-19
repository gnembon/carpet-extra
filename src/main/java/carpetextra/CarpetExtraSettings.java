package carpetextra;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.BUGFIX;
import static carpet.settings.RuleCategory.COMMAND;
import static carpet.settings.RuleCategory.CREATIVE;
import static carpet.settings.RuleCategory.DISPENSER;
import static carpet.settings.RuleCategory.EXPERIMENTAL;
import static carpet.settings.RuleCategory.FEATURE;
import static carpet.settings.RuleCategory.SURVIVAL;

/**
 * Here is your example Settings class you can plug to use carpetmod /carpet settings command
 */
public class CarpetExtraSettings
{
    public static final String EXTRA = "extras";

    public static class validatorScaffoldingDistance extends Validator<Integer> {
        @Override
        public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String string) {
            return newValue >= 0 && newValue <= 7 ? newValue : null;
        }

        @Override
        public String description() { return "You must choose a value from 0 to 7";}
    }
    @Rule(
            desc="Max distance for scaffolding",
            options = { "2", "3", "5", "7" },
            category = {FEATURE, EXTRA},
            strict = false,
            validate = validatorScaffoldingDistance.class
    )
    public static int scaffoldingDistance = 7;

    @Rule(
            desc = "Auto-crafting dropper",
            extra = {"Is a dropper points to the crafting table ",
                    "and has a valid recipe in its 3x3 it crafts it.",
                    "Overrides comparators so they indicate number of filled slots instead",
                    "Also makes hoppers, droppers and dispensers input max 1 item per slot"
            },
            category = {CREATIVE, EXTRA, DISPENSER}
    )
    public static boolean autoCraftingDropper = false;

    @Rule(desc="Dispensers can place blocks", category = {CREATIVE, EXTRA, DISPENSER})
    public static boolean dispenserPlacesBlocks = false;

    @Rule(
            desc = "variable delays on wooden components",
            extra = "buttons, pressure plates",
            category = {EXTRA, FEATURE}
    )
    public static boolean variableWoodDelays = false;


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
            desc = "Places the mined block in the player inventory when sneaking.",
            category = {FEATURE,EXTRA}
    )
    public static boolean carefulBreak= false;

    @Rule(
            desc = "Allows Hopper Minecarts to transfer items out.",
            category = {BUGFIX,FEATURE,EXTRA,EXPERIMENTAL}
    )
    public static boolean hopperMinecartItemTransfer= false;
    
    @Rule(desc = "Enables /ping for players to get their ping", category = {COMMAND, EXTRA})
    public static boolean commandPing = true;
    
    /*@Rule(
            desc = "Water bottles in dispensers fill with water when dispensed with water in front.",
            category = {EXPERIMENTAL, FEATURE, EXTRA, DISPENSER}
    )
    public static boolean dispensersFillBottles;*/
    
    @Rule(
            desc = "Minecarts can be filled with hoppers, chests, tnt and furnace.",
            category = {EXPERIMENTAL, FEATURE, EXTRA, DISPENSER}
    )
    public static boolean dispensersFillMinecarts = false;
    
    @Rule(desc = "Wet sponges dry in the nether dimension", category = {EXTRA, FEATURE})
    public static boolean spongesDryInTheNether = false;

    @Rule(
            desc = "Clerics can warm nether farts",
            extra = {"This will also make them pick up wart items","As well as pathfind to soulsand"},
            category = {EXTRA, FEATURE}
    )
    public static boolean clericsFarmWarts = false;
    
    @Rule(desc = "Multiple ice crushed by falling anvils make packed ice", category = {EXTRA, EXPERIMENTAL, FEATURE})
    public static boolean renewablePackedIce = false;
    
    @Rule(
            desc = "Dispensers can play records if there's a jukebox in front of them",
            extra = "If record already exists in the jukebox, it gets placed back in the dispenser",
            category = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersPlayRecords = false;

    @Rule(
            desc = "Client can provide alternative block placement",
            category = {EXTRA, SURVIVAL}
    )
    public static boolean accurateBlockPlacement = false;

    @Rule(
            desc = "Dispensers can toggle with a stick things like buttons, doors, repeaters",
            category = {EXTRA, EXPERIMENTAL, FEATURE, DISPENSER}
    )
    public static boolean dispensersToggleThings = false;

    @Rule(desc = "Dispensers with hoes can till soil", category = {EXTRA, FEATURE, DISPENSER})
    public static boolean dispensersTillSoil = false;

    @Rule(desc = "Dispensers can feed animals", category = {EXTRA, EXPERIMENTAL, FEATURE, DISPENSER})
    public static boolean dispensersFeedAnimals = false;
    
    @Rule(desc = "Disables player entity collision", category = {EXTRA, CREATIVE, EXPERIMENTAL})
    public static boolean disablePlayerCollision = false;
    
    @Rule(
            desc = "Obsidian surrounded by 6 lava sources has a chance of converting to lava",
            category = {EXTRA, EXPERIMENTAL, FEATURE},
            extra = "Credits: Skyrising"
    )
    public static boolean renewableLava = false;
    
    @Rule(
            desc = "1.8 double retraction from pistons",
            category = {EXTRA, EXPERIMENTAL},
            extra = { "Gives pistons the ability to double retract without side effects" }
    )
    public static boolean doubleRetraction = false;
    
    @Rule(
            desc = "Fixes block states in F3 debug mode not updating for some blocks. May cause increased network traffic",
            category = {EXTRA, EXPERIMENTAL},
            extra = "Works with cactus, sugar cane, saplings, hoppers, dispensers and droppers."
    )
    public static boolean blockStateSyncing = false;
    
    @Rule(desc = "Cobblestone crushed by falling anvils makes sand", category = {EXTRA, EXPERIMENTAL, FEATURE})
    public static boolean renewableSand = false;
    
    @Rule(desc = "Reintroduce Dragon Egg Bedrock breaking bug from 1.12", category = {EXTRA, EXPERIMENTAL})
    public static boolean dragonEggBedrockBreaking = false;
    
    @Rule(
            desc = "Fire charges from dispensers convert cobblestone to netherrack",
            category = {EXTRA, EXPERIMENTAL},
            extra = "Credits: Skyrising"
    )
    public static boolean fireChargeConvertsToNetherrack = false;
    
    @Rule(
            desc = "Chickens can be sheared to get feathers. Beware! every time u shear a chicken, it gets damaged!",
            category = {EXTRA, FEATURE},
            extra = "Baby chickens can't be sheared"
    )
    public static boolean chickenShearing = false;
    
    @Rule(
            desc = "If a living entity dies on sand with fire on top the sand will convert into soul sand",
            category = {EXTRA, FEATURE, EXPERIMENTAL}
    )
    public static boolean mobInFireConvertsSandToSoulsand = false;
    
    @Rule(
            desc = "Place a wither rose in a flowerpot to load that chunk",
            extra = "If u enable the rule the already existing chunks with flowerpots won't be loaded. " +
                    "Also disabling the carpet rule won't remove the loaded chunks, u need to manually unload them using the /forceload command. " +
                    "All the loaded chunks can be seen using `/forceload query`",
            category = {EXTRA, FEATURE, EXPERIMENTAL}
    )
    public static boolean flowerPotChunkLoading = false;
    
    @Rule(
            desc = "Won't let mobs glitch into blocks when reloaded.",
            extra = "Can cause slight differences in mobs behaviour. Fixes MC-2025",
            category = {EXTRA, BUGFIX, EXPERIMENTAL}
    )
    public static boolean reloadSuffocationFix = false;
    
    @Rule(
            desc = "Dispensers with empty buckets can milk cows",
            category = {EXPERIMENTAL, EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersMilkCows = false;
    
    @Rule(
            desc = "Quick pulses won't get lost in repeater setups",
            extra = "Probably brings back pre 1.8 behaviour. Fixes MC-54711",
            category = {EXTRA, BUGFIX, EXPERIMENTAL}
    )
    public static boolean repeaterPriorityFix = false;
    
    @Rule(desc = "Only strays spawn in igloos", category = {FEATURE, EXTRA})
    public static boolean straySpawningInIgloos = false;
    
    @Rule(desc = "Skeletons turn into wither skeletons when struck by lightning", category = {FEATURE, EXTRA})
    public static boolean renewableWitherSkeletons = false;
    
    @Rule(desc = "Only creepers spawn in jungle temples", category = {FEATURE, EXTRA})
    public static boolean creeperSpawningInJungleTemples = false;
    
    @Rule(
            desc = "Let dragon eggs break Y0 bedrock",
            extra = "Requires dragonEggBedrockBreaking to be set to true",
            category = {EXPERIMENTAL, EXTRA}
    )
    public static boolean y0DragonEggBedrockBreaking = false;
    
    public static class ValidateSpiderJokeyDropChance extends Validator<Integer>
    {
        @Override
        public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String string)
        {
            return newValue >= 0 && newValue <= 100 ? newValue : null;
        }
    
        @Override
        public String description() { return "You must choose a value from 0 to 100";}
    }
    
    @Rule(
            desc = "Gives Spider jockeys a specified chance to drop enchanted golden apples",
            extra = "0 is default, i.e no enchanted golden apples will be dropped",
            options = {"0", "50", "100"},
            category = {EXTRA, FEATURE},
            validate = ValidateSpiderJokeyDropChance.class
    )
    public static int spiderJockeysDropGapples = 0;
    
    @Rule(
            desc = "Dragon's breath from dispensers convert cobblestone to end stone",
            category = {EXTRA, EXPERIMENTAL, DISPENSER}
    )
    public static boolean dragonsBreathConvertsCobbleToEndstone = false;
    
    // Implemented in 1.15 - doInsomnia
    /*@Rule(desc = "Disable phantom spawning", category = {CREATIVE, EXTRA})
    public static boolean disablePhantomSpawning = false;*/
}
