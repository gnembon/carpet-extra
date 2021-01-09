# carpet-extra
Extra Features for Carpet Mod.

Use along side base [fabric-carpet](https://github.com/gnembon/fabric-carpet) mod for the same minecraft version.

Due to how autoCraftingTable feature is implemented, it has been moved to [a standalone extension](https://github.com/gnembon/carpet-autoCraftingTable).

# Carpet Extra Features

## accurateBlockPlacement
Client can provide alternative block placement.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `SURVIVAL`  
  
## autoCraftingDropper
Auto-crafting dropper  
If a dropper points into a crafting table and contains a valid 3x3 crafting recipe, firing that   
dropper will cause it to craft (drop as item) that recipe.   
Overrides comparators so they indicate number of filled slots instead.  
Also makes hoppers, droppers and dispensers input max 1 item per slot.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `CREATIVE`, `EXTRAS`, `DISPENSER`  
  
## betterBonemeal
Bonemeal can be used to grow sugarcane and cactus.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`, `SURVIVAL`  
  
## blazeMeal
Blaze powder fertilizes netherwart.  
Via dispenser or player right click actions.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `SURVIVAL`  
  
## blockStateSyncing
Fixes block states in F3 debug mode not updating for some blocks.  
May cause increased network traffic.  
Works with cactus, sugar cane, saplings, hoppers, dispensers and droppers.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## chickenShearing
Chickens can be sheared to get feathers. Beware! every time u shear a chicken, it gets damaged!  
Baby chickens can't be sheared.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  
  
## clericsFarmWarts
Clerics can warm nether farts.  
This will also allow them to pick up wart items, as well as pathfind to soulsand.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  
  
## commandPing
Enables `/ping` for players to get their ping.  
* Type: `boolean`  
* Default value: `true`  
* Required options: `true`, `false`  
* Categories: `COMMAND`, `EXTRAS`  
* Additional notes:  
  * It has an accompanying command  
  
## comparatorReadsClock
Allows Comparators to read the daytime instead of the rotation of clocks in item frames.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`, `EXPERIMENTAL`  
  
## creeperSpawningInJungleTemples
Only creepers spawn in jungle temples.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`  
  
## disablePlayerCollision
Disables player entity collision.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `CREATIVE`, `EXPERIMENTAL`  
  
## dispenserPlacesBlocks
Dispensers can place blocks.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `CREATIVE`, `EXTRAS`, `DISPENSER`  
  
## dispensersCarvePumpkins
Dispensers containing shears can carve pumpkins.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersFeedAnimals
Dispensers can feed animals.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`, `DISPENSER`  
  
## dispensersFillMinecarts
Minecarts can be filled with hoppers, chests, tnt and furnace.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXPERIMENTAL`, `FEATURE`, `EXTRAS`, `DISPENSER`  
  
## dispensersMilkCows
Dispensers with empty buckets can milk cows and get stew from mooshrooms with bowls.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXPERIMENTAL`, `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersPlayRecords
Dispensers can play records if there's a jukebox in front of them.  
If a record already exists in the jukebox, it gets placed back in the dispenser.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersTillSoil
Dispensers with hoes can till soil.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersToggleThings
Dispensers containing a stick can toggle things.  
Works with buttons, redstone, noteblocks, comparators, repeaters,   
daylight detectors, etc.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`, `DISPENSER`  
  
## doubleRetraction
Re-adds 1.8 double retraction to pistons.  
Gives pistons the ability to double retract without side effects.  
Fixes [MC-88959](https://bugs.mojang.com/browse/MC-88959).  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## dragonEggBedrockBreaking
Reintroduce the Dragon Egg Bedrock breaking bug from 1.12.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## dragonsBreathConvertsCobbleToEndstone
Dragon's breath from dispensers convert cobblestone to end stone.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `DISPENSER`  
  
## emptyShulkerBoxStackAlways
Empty Shulker Boxes will always stack, even inside inventories.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`, `EXPERIMENTAL`  
  
## enderPearlChunkLoading
Allow horizontally moving Ender Pearls to load chunks as entity ticking.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`  
  
## fireChargeConvertsToNetherrack
Fire charges from dispensers convert cobblestone to netherrack.  
Credits: Skyrising  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## flowerPotChunkLoading
Placing a wither rose in a flowerpot will load that chunk.  
If u enable the rule the already existing chunks with flowerpots won't be loaded.   
Also disabling the carpet rule won't remove the loaded chunks, u need to manually unload them using the /forceload command.   
All the loaded chunks can be seen using `/forceload query`  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `EXPERIMENTAL`  
  
## hopperMinecart8gtCooldown
Makes Hopper Minecarts have an 8gt cooldown like hoppers.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `BUGFIX`, `FEATURE`, `EXTRAS`, `EXPERIMENTAL`  
  
## hopperMinecartItemTransfer
Allows Hopper Minecarts to transfer items into containers below them.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `BUGFIX`, `FEATURE`, `EXTRAS`, `EXPERIMENTAL`  
  
## maxSpongeRange
Maximum offset limit for sponge.  
* Type: `int`  
* Default value: `7`  
* Suggested options: `7`  
* Categories: `FEATURE`, `EXTRAS`  
  
## maxSpongeSuck
Maximum water sucking for sponge.  
* Type: `int`  
* Default value: `64`  
* Suggested options: `64`  
* Categories: `FEATURE`, `EXTRAS`  
  
## mobInFireConvertsSandToSoulsand
If a living entity dies on sand with fire on top the sand will convert into soul sand.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `EXPERIMENTAL`  
  
## pistonRedirectsRedstone
Pistons and sticky pistons redirect redstone  
When retracting, they will blink visually  
but that's only to minimize changes required for it to work  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  
  
## reloadSuffocationFix
Won't let mobs glitch into blocks when reloaded.  
Can cause slight differences in mobs behaviour.  
Fixes [MC-2025](https://bugs.mojang.com/browse/MC-2025).  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `BUGFIX`, `EXPERIMENTAL`  
  
## renewableLava
Obsidian surrounded by 6 lava sources has a chance of converting to lava.  
Credits: Skyrising  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`  
  
## renewablePackedIce
Multiple ice crushed by falling anvils make packed ice.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`  
  
## renewableSand
Cobblestone crushed by falling anvils makes sand.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`  
  
## renewableWitherSkeletons
Skeletons turn into wither skeletons when struck by lightning.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`  
  
## repeaterPriorityFix
Quick pulses won't get lost in repeater setups.  
Probably brings back pre 1.8 behaviour.  
Fixes [MC-54711](https://bugs.mojang.com/browse/MC-54711).  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `BUGFIX`, `EXPERIMENTAL`  
  
## scaffoldingDistance
Max distance for scaffolding.  
* Type: `int`  
* Default value: `7`  
* Suggested options: `2`, `3`, `5`, `7`  
* Categories: `FEATURE`, `EXTRAS`  
* Additional notes:  
  * You must choose a value from 0 to 7  
  
## spiderJockeysDropGapples
Gives Spider jockeys a specified chance to drop enchanted golden apples.  
0 is the default setting, no enchanted golden apples will be dropped  
* Type: `int`  
* Default value: `0`  
* Required options: `0`, `50`, `100`  
* Categories: `EXTRAS`, `FEATURE`  
* Additional notes:  
  * You must choose a value from 0 to 100  
  
## straySpawningInIgloos
Only strays spawn in igloos.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`  
  
## updateSuppressionCrashFix
Fixes updates suppression causing server crashes.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `BUGFIX`, `EXTRAS`  
  
## variableWoodDelays
Variable delays on wooden components (buttons, pressure plates).  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  
  
## y0DragonEggBedrockBreaking
Let dragon eggs break Y0 bedrock.  
Requires dragonEggBedrockBreaking to be set to true.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXPERIMENTAL`, `EXTRAS`  
  


