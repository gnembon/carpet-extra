<img src="./src/main/resources/assets/carpet-extra/icon.png" align="right" width="128px"/>

# Carpet Extra

[![Development Builds](https://github.com/gnembon/carpet-extra/actions/workflows/devbuild.yml/badge.svg)](https://github.com/gnembon/carpet-extra/actions/workflows/devbuild.yml)
[![CurseForge downloads](http://cf.way2muchnoise.eu/full_349240_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/carpet-extra)
[![Modrinth downloads](https://img.shields.io/modrinth/dt/carpet-extra?label=Modrinth%20downloads&logo=modrinth)](https://modrinth.com/mod/carpet-extra)
[![GitHub downloads](https://img.shields.io/github/downloads/gnembon/carpet-extra/total?label=Github%20downloads&logo=github)](https://github.com/gnembon/carpet-extra/releases)
[![GitHub contributors](https://img.shields.io/github/contributors/gnembon/carpet-extra?label=Contributors&logo=github)](https://github.com/gnembon/carpet-extra/graphs/contributors)
[![Discord](https://badgen.net/discord/online-members/gn99m4QRY4?icon=discord&label=Discord&list=what)](https://discord.gg/gn99m4QRY4)

Carpet Extra is an extension adding extra features to [Fabric Carpet], like countless new dispenser behaviours, new ways to get resources in a renewable way and many others!

[Fabric Carpet]: https://github.com/gnembon/fabric-carpet

For its enabled dispenser behaviours it also adds a new [Scarpet event] that triggers when those happen, allowing you to further customize them.

[Scarpet event]: /docs/scarpet/ScarpetEvents.md

## More extensions

If you're looking for Carpet autocrafting table, it has been moved to a standalone extension you can find on [its repository on Github][Autocraftingtable repo].

[Autocraftingtable repo]: https://github.com/gnembon/carpet-autoCraftingTable

There are also lots of other carpet extensions out there, adding countless new rules and functionality! You can find a list of them [in the Carpet wiki][extension list].

[extension list]: https://github.com/gnembon/fabric-carpet/wiki/List-of-Carpet-extensions

# Carpet Extra Features
# Carpet Mod Settings
## accurateBlockPlacement
Client can provide alternative block placement.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `SURVIVAL`  
  
## autoCraftingDropper
Auto-crafting dropper  
If a dropper points into a crafting table and contains a valid 3x3 crafting recipe, firing that   
dropper will cause it to craft (drop as item) that recipe.   
Overrides comparators so they indicate number of filled slots instead.  
Also makes hoppers, droppers and dispensers input max 1 item per slot.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `CREATIVE`, `EXTRAS`, `DISPENSER`  
  
## betterBonemeal
Bonemeal can be used to grow sugarcane, cactus and lily pads.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`, `SURVIVAL`  
  
## blazeMeal
Blaze powder fertilizes netherwart.  
Via dispenser or player right click actions.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `SURVIVAL`  
  
## blockStateSyncing
Fixes block states in F3 debug mode not updating for some blocks.  
May cause increased network traffic.  
Works with cactus, sugar cane and saplings.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## chickenShearing
Chickens can be sheared to get feathers. Beware! every time u shear a chicken, it gets damaged!  
Baby chickens can't be sheared.  
Also works with dispensers  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## clericsFarmWarts
Clerics can warm nether farts.  
This will also allow them to pick up wart items, as well as pathfind to soulsand.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  
  
## commandPing
Enables `/ping` for players to get their ping.  
* Type: `Boolean`  
* Default value: `true`  
* Allowed options: `true`, `false`  
* Categories: `COMMAND`, `EXTRAS`  
* Additional notes:  
  * It has an accompanying command  
  
## comparatorBetterItemFrames
Allows Comparators to see item frames that are horizontal in front of them and on top the the block in front of them  
Behind: Allows comparators to detect item frames in the block behind them  
Lenient: Allows comparators to detect any item frames within the block behind a full block  
Extended: Allows comparators to detect item frames on a full block behind the comparator  
* Type: `ComparatorOptions`  
* Default value: `vanilla`  
* Allowed options: `vanilla`, `behind`, `lenient`, `extended`  
* Categories: `FEATURE`, `EXTRAS`, `EXPERIMENTAL`  
  
## comparatorReadsClock
Allows Comparators to read the daytime instead of the rotation of clocks in item frames.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`, `EXPERIMENTAL`  
  
## creeperSpawningInJungleTemples
Only creepers spawn in jungle temples.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`  
  
## disablePlayerCollision
Disables player entity collision.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `CREATIVE`, `EXPERIMENTAL`  
  
## dispenserPlacesBlocks
Dispensers can place blocks.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `CREATIVE`, `EXTRAS`, `DISPENSER`  
  
## dispensersCarvePumpkins
Dispensers containing shears can carve pumpkins.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersFeedAnimals
Dispensers can feed animals if given their breeding item.  
Can also feed flowers to brown mooshrooms to give them a suspicious stew effect  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`, `DISPENSER`  
  
## dispensersFillMinecarts
Minecarts can be filled with hoppers, chests, tnt and furnace.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXPERIMENTAL`, `FEATURE`, `EXTRAS`, `DISPENSER`  
  
## dispensersMilkAnimals
Dispensers with empty buckets can milk cows/mooshrooms/goats, and get mushroom/suspicious stew from mooshrooms with bowls.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXPERIMENTAL`, `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersPlaceBoatsOnIce
Dispensers can place boats on ice  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersPotPlants
Dispensers can put flowers in flower pots  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersStripBlocks
Dispensers with axes can strip blocks  
Can strip logs, remove oxidation, and remove wax  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersTillSoil
Dispensers with hoes can till soil.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersToggleThings
Dispensers containing a stick can toggle/activate things.  
Works with buttons, redstone, noteblocks, comparators, repeaters,   
daylight detectors, etc.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`, `DISPENSER`  
  
## dispensersUseCauldrons
Dispensers can empty/fill cauldrons with buckets or bottles, and undye leather armor/shulker boxes/banners  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## doubleRetraction
Re-adds 1.8 double retraction to pistons.  
Gives pistons the ability to double retract without side effects.  
Fixes [MC-88959](https://bugs.mojang.com/browse/MC-88959).  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## dragonEggBedrockBreaking
Reintroduce the Dragon Egg Bedrock breaking bug from 1.12.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## emptyShulkerBoxStackAlways
Empty Shulker Boxes will always stack, even inside inventories.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`, `EXPERIMENTAL`  
  
## fallingBlockDispensers
Dispensers/Droppers with a block in front of them when powered will turn that block into a falling block  
dispenser & dropper give the same velocity to the falling block like they do in 22w13oneblockatatime  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## flowerPotChunkLoading
Placing a wither rose in a flowerpot will load that chunk.  
If u enable the rule the already existing chunks with flowerpots won't be loaded.   
Also disabling the carpet rule won't remove the loaded chunks, u need to manually unload them using the /forceload command.   
All the loaded chunks can be seen using `/forceload query`  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `EXPERIMENTAL`  
  
## hopperMinecartItemTransfer
Allows Hopper Minecarts to transfer items into containers below them.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `BUGFIX`, `FEATURE`, `EXTRAS`, `EXPERIMENTAL`  
  
## maxSpongeRange
Maximum offset limit for sponge.  
* Type: `Integer`  
* Default value: `7`  
* Suggested options: `7`  
* Categories: `FEATURE`, `EXTRAS`  
  
## maxSpongeSuck
Maximum water sucking for sponge.  
* Type: `Integer`  
* Default value: `64`  
* Suggested options: `64`  
* Categories: `FEATURE`, `EXTRAS`  
  
## mobInFireConvertsSandToSoulsand
If a living entity dies on sand with fire on top the sand will convert into soul sand.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `EXPERIMENTAL`  
  
## pistonRedirectsRedstone
Pistons and sticky pistons redirect redstone  
When retracting, they will blink visually  
but that's only to minimize changes required for it to work  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  
  
## reloadSuffocationFix
Won't let mobs glitch into blocks when reloaded.  
Can cause slight differences in mobs behaviour.  
Fixes [MC-2025](https://bugs.mojang.com/browse/MC-2025).  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `BUGFIX`, `EXPERIMENTAL`  
  
## renewableEndstone
Dragon's breath from dispensers convert cobblestone to end stone.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `DISPENSER`  
  
## renewableIce
Multiple ice crushed by falling anvils make denser ice.  
frosted turns into normal, normal into packed and packed into blue  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`  
  
## renewableNetherrack
Fire charges from dispensers convert cobblestone to netherrack.  
Credits: Skyrising  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## renewableSand
Cobblestone crushed by falling anvils makes sand.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`  
  
## renewableWitherSkeletons
Skeletons turn into wither skeletons when struck by lightning.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`  
  
## repeaterPriorityFix
Quick pulses won't get lost in repeater setups.  
Probably brings back pre 1.8 behaviour.  
Fixes [MC-54711](https://bugs.mojang.com/browse/MC-54711).  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `BUGFIX`, `EXPERIMENTAL`  
  
## scaffoldingDistance
Max distance for scaffolding.  
* Type: `Integer`  
* Default value: `7`  
* Suggested options: `2`, `3`, `5`, `7`  
* Categories: `FEATURE`, `EXTRAS`  
* Additional notes:  
  * You must choose a value from 0 to 7  
  
## spiderJockeysDropGapples
Gives Spider jockeys a specified chance to drop enchanted golden apples.  
0 is the default setting, no enchanted golden apples will be dropped  
* Type: `Integer`  
* Default value: `0`  
* Suggested options: `0`, `50`, `100`  
* Categories: `EXTRAS`, `FEATURE`  
* Additional notes:  
  * You must choose a value from 0 to 100  
  
## straySpawningInIgloos
Only strays spawn in igloos.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`  
  
## variableWoodDelays
Variable delays on wooden components (buttons, pressure plates).  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  
  
## xpPerSculkCatalyst
Sets the amount of xp dropped when breaking a sculk catalyst.  
* Type: `Integer`  
* Default value: `5`  
* Suggested options: `5`, `10`, `20`  
* Categories: `EXTRAS`, `FEATURE`  
* Additional notes:  
  * Must be a positive number or 0  
  
## y0DragonEggBedrockBreaking
Let dragon eggs break Y0 bedrock.  
Requires dragonEggBedrockBreaking to be set to true.  
* Type: `Boolean`  
* Default value: `false`  
* Allowed options: `true`, `false`  
* Categories: `EXPERIMENTAL`, `EXTRAS`  
  
