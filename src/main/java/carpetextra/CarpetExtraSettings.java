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
    public enum ComparatorOptions {
        VANILLA(0),
        BEHIND(1),
        LENIENT(2),
        EXTENDED(3);

        public final int id;

        ComparatorOptions(int id) {
            this.id = id;
        }
    }

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
            desc = "Max distance for scaffolding.",
            options = { "2", "3", "5", "7" },
            category = {FEATURE, EXTRA},
            strict = false,
            validate = validatorScaffoldingDistance.class
    )
    public static int scaffoldingDistance = 7;

    @Rule(
            desc = "Pistons and sticky pistons redirect redstone",
            extra = {"When retracting, they will blink visually",
                    "but that's only to minimize changes required for it to work"
            },
            category = {EXTRA, FEATURE}
    )
    public static boolean pistonRedirectsRedstone = false;

    @Rule(
            desc = "Auto-crafting dropper",
            extra = {"If a dropper points into a crafting table and contains a valid 3x3 crafting recipe, firing that ",
                    "dropper will cause it to craft (drop as item) that recipe. ",
                    "Overrides comparators so they indicate number of filled slots instead.",
                    "Also makes hoppers, droppers and dispensers input max 1 item per slot."
            },
            category = {CREATIVE, EXTRA, DISPENSER}
    )
    public static boolean autoCraftingDropper = false;

    @Rule(desc = "Dispensers can place blocks.", category = {CREATIVE, EXTRA, DISPENSER})
    public static boolean dispenserPlacesBlocks = false;

    @Rule(
            desc = "Variable delays on wooden components (buttons, pressure plates).",
            category = {EXTRA, FEATURE}
    )
    public static boolean variableWoodDelays = false;


    @Rule(
        desc = "Allows Comparators to read the daytime instead of the rotation of clocks in item frames.",
        category = {FEATURE,EXTRA,EXPERIMENTAL}
    )
    public static boolean comparatorReadsClock = false;

    @Rule(
            desc = "Allows Comparators to see item frames that are horizontal in front of them and on top the the block in front of them",
            extra = {
                    "Behind: Allows comparators to detect item frames in the block behind them",
                    "Lenient: Allows comparators to detect any item frames within the block behind a full block",
                    "Extended: Allows comparators to detect item frames on a full block behind the comparator"
            },
            category = {FEATURE,EXTRA,EXPERIMENTAL}
    )
    public static ComparatorOptions comparatorBetterItemFrames = ComparatorOptions.VANILLA;

    @Rule(
        desc = "Makes Hopper Minecarts have an 8gt cooldown like hoppers.",
        category = {BUGFIX,FEATURE,EXTRA,EXPERIMENTAL}
    )
    public static boolean hopperMinecart8gtCooldown = false;

    @Rule(
        desc = "Allows Hopper Minecarts to transfer items into containers below them.",
        category = {BUGFIX,FEATURE,EXTRA,EXPERIMENTAL}
    )
    public static boolean hopperMinecartItemTransfer = false;
    
    @Rule(desc = "Enables `/ping` for players to get their ping.", category = {COMMAND, EXTRA})
    public static boolean commandPing = true;

    @Rule(
            desc = "Minecarts can be filled with hoppers, chests, tnt and furnace.",
            category = {EXPERIMENTAL, FEATURE, EXTRA, DISPENSER}
    )
    public static boolean dispensersFillMinecarts = false;

    @Rule(
            desc = "Clerics can warm nether farts.",
            extra = {"This will also allow them to pick up wart items, as well as pathfind to soulsand."},
            category = {EXTRA, FEATURE}
    )
    public static boolean clericsFarmWarts = false;
    
    @Rule(
            desc = "Multiple ice crushed by falling anvils make denser ice.",
            extra = "frosted turns into normal, normal into packed and packed into blue",
            category = {EXTRA, EXPERIMENTAL, FEATURE}
    )
    public static boolean renewableIce = false;
    
    @Rule(
            desc = "Dispensers can play records if there's a jukebox in front of them.",
            extra = "If a record already exists in the jukebox, it gets placed back in the dispenser.",
            category = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersPlayRecords = false;

    @Rule(
            desc = "Client can provide alternative block placement.",
            category = {EXTRA, SURVIVAL}
    )
    public static boolean accurateBlockPlacement = false;

    @Rule(
            desc = "Dispensers containing a stick can toggle/activate things.",
            extra = {"Works with buttons, redstone, noteblocks, comparators, repeaters, ", "daylight detectors, etc."},
            category = {EXTRA, EXPERIMENTAL, FEATURE, DISPENSER}
    )
    public static boolean dispensersToggleThings = false;

    @Rule(desc = "Dispensers with hoes can till soil.", category = {EXTRA, FEATURE, DISPENSER})
    public static boolean dispensersTillSoil = false;

    @Rule(
            desc = "Dispensers can feed animals if given their breeding item.",
            extra = "Can also feed flowers to brown mooshrooms to give them a suspicious stew effect",
            category = {EXTRA, EXPERIMENTAL, FEATURE, DISPENSER}
    )
    public static boolean dispensersFeedAnimals = false;
    
    @Rule(desc = "Disables player entity collision.", category = {EXTRA, CREATIVE, EXPERIMENTAL})
    public static boolean disablePlayerCollision = false;
    
    @Rule(
            desc = "Obsidian surrounded by 6 lava sources has a chance of converting to lava.",
            category = {EXTRA, EXPERIMENTAL, FEATURE},
            extra = "Credits: Skyrising"
    )
    public static boolean renewableLava = false;
    
    @Rule(
            desc = "Re-adds 1.8 double retraction to pistons.",
            category = {EXTRA, EXPERIMENTAL},
            extra = { "Gives pistons the ability to double retract without side effects.",
                      "Fixes [MC-88959](https://bugs.mojang.com/browse/MC-88959)." }
    )
    public static boolean doubleRetraction = false;
    
    @Rule(
            desc = "Fixes block states in F3 debug mode not updating for some blocks.",
            category = {EXTRA, EXPERIMENTAL},
            extra = {"May cause increased network traffic.", "Works with cactus, sugar cane, saplings, hoppers, dispensers and droppers."}
    )
    public static boolean blockStateSyncing = false;
    
    @Rule(desc = "Cobblestone crushed by falling anvils makes sand.", category = {EXTRA, EXPERIMENTAL, FEATURE})
    public static boolean renewableSand = false;


    @Rule(desc = "Reintroduce the Dragon Egg Bedrock breaking bug from 1.12.", category = {EXTRA, EXPERIMENTAL})
    public static boolean dragonEggBedrockBreaking = false;
    
    @Rule(
            desc = "Fire charges from dispensers convert cobblestone to netherrack.",
            category = {EXTRA, EXPERIMENTAL},
            extra = "Credits: Skyrising"
    )
    public static boolean renewableNetherrack = false;
    
    @Rule(
            desc = "Chickens can be sheared to get feathers. Beware! every time u shear a chicken, it gets damaged!",
            category = {EXTRA, FEATURE, DISPENSER},
            extra = {"Baby chickens can't be sheared.", "Also works with dispensers"}
    )
    public static boolean chickenShearing = false;
    
    @Rule(
            desc = "If a living entity dies on sand with fire on top the sand will convert into soul sand.",
            category = {EXTRA, FEATURE, EXPERIMENTAL}
    )
    public static boolean mobInFireConvertsSandToSoulsand = false;
    
    @Rule(
            desc = "Placing a wither rose in a flowerpot will load that chunk.",
            extra = {
                    "If u enable the rule the already existing chunks with flowerpots won't be loaded. ",
                    "Also disabling the carpet rule won't remove the loaded chunks, u need to manually unload them using the /forceload command. ",
                    "All the loaded chunks can be seen using `/forceload query`"
            },
            category = {EXTRA, FEATURE, EXPERIMENTAL}
    )
    public static boolean flowerPotChunkLoading = false;
    
    @Rule(
            desc = "Won't let mobs glitch into blocks when reloaded.",
            extra = {"Can cause slight differences in mobs behaviour.", "Fixes [MC-2025](https://bugs.mojang.com/browse/MC-2025)."},
            category = {EXTRA, BUGFIX, EXPERIMENTAL}
    )
    public static boolean reloadSuffocationFix = false;
    
    @Rule(
            desc = "Dispensers with empty buckets can milk cows/mooshrooms/goats, and get mushroom/suspicious stew from mooshrooms with bowls.",
            category = {EXPERIMENTAL, EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersMilkAnimals = false;
    
    @Rule(
            desc = "Quick pulses won't get lost in repeater setups.",
            extra = {"Probably brings back pre 1.8 behaviour.", "Fixes [MC-54711](https://bugs.mojang.com/browse/MC-54711)."},
            category = {EXTRA, BUGFIX, EXPERIMENTAL}
    )
    public static boolean repeaterPriorityFix = false;
    
    @Rule(desc = "Only strays spawn in igloos.", category = {FEATURE, EXTRA})
    public static boolean straySpawningInIgloos = false;
    
    @Rule(desc = "Skeletons turn into wither skeletons when struck by lightning.", category = {FEATURE, EXTRA})
    public static boolean renewableWitherSkeletons = false;
    
    @Rule(desc = "Only creepers spawn in jungle temples.", category = {FEATURE, EXTRA})
    public static boolean creeperSpawningInJungleTemples = false;
    
    @Rule(
            desc = "Let dragon eggs break Y0 bedrock.",
            extra = "Requires dragonEggBedrockBreaking to be set to true.",
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
            desc = "Gives Spider jockeys a specified chance to drop enchanted golden apples.",
            extra = "0 is the default setting, no enchanted golden apples will be dropped",
            options = {"0", "50", "100"},
            category = {EXTRA, FEATURE},
            strict = false,
            validate = ValidateSpiderJokeyDropChance.class
    )
    public static int spiderJockeysDropGapples = 0;
    
    @Rule(
            desc = "Dragon's breath from dispensers convert cobblestone to end stone.",
            category = {EXTRA, EXPERIMENTAL, DISPENSER}
    )
    public static boolean renewableEndstone = false;

    @Rule(
            desc = "Maximum water sucking for sponge.",
            options = {"64"},
            category = {FEATURE, EXTRA},
            strict = false
    )
    public static int maxSpongeSuck = 64;

    @Rule(
            desc = "Maximum offset limit for sponge.",
            options = {"7"},
            category = {FEATURE, EXTRA},
            strict = false
    )
    public static int maxSpongeRange = 7;

    @Rule(
            desc = "Empty Shulker Boxes will always stack, even inside inventories.",
            category = {FEATURE, EXTRA, EXPERIMENTAL}
    )
    public static boolean emptyShulkerBoxStackAlways = false;

    @Rule(
            desc = "Allow horizontally moving Ender Pearls to load chunks as entity ticking.",
            category = {FEATURE, EXTRA}
    )
    public static boolean enderPearlChunkLoading = false;

    @Rule(
            desc = "Bonemeal can be used to grow sugarcane, cactus and lily pads.",
            category = {FEATURE, EXTRA, SURVIVAL}
    )
    public static boolean betterBonemeal = false;

    @Rule(
            desc = "Dispensers containing shears can carve pumpkins.",
            category = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersCarvePumpkins = false;
    
    @Rule(
            desc = "Blaze powder fertilizes netherwart.",
            extra = "Via dispenser or player right click actions.",
            category = {EXTRA, FEATURE, SURVIVAL}
    )
    public static boolean blazeMeal = false;

    @Rule(
            desc = "Dispensers with axes can strip blocks",
            extra = "Can strip logs, remove oxidation, and remove wax",
            category = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersStripBlocks = false;

    @Rule(
            desc = "Dispensers can empty/fill cauldrons with buckets or bottles, and undye leather armor/shulker boxes/banners",
            category = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersUseCauldrons = false;

    @Rule(
            desc = "Dispensers can put flowers in flower pots",
            category = {EXTRA, FEATURE, DISPENSER}
    )
    public static boolean dispensersPotPlants = false;
}
