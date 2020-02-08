# carpet-extra
Extra Features for Carpet Mod

Use along side base fabric-carpet mod for the same minecraft version.

Due to how autoCraftingTable feature is implemented, if you are using other mods that require you to use fabric-api (carpet doesn't need it, btw), and you run carpet-extra on a server, the clients must run the same mod configuration as well. If you are only using carpet-style mods, you can connect to the servers with whatever vanilla compatible client you are using.

# Carpet Extra features
## accurateBlockPlacement
Client can provide alternative block placement  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `SURVIVAL`  
  
## autoCraftingDropper
Auto-crafting dropper  
Is a dropper points to the crafting table   
and has a valid recipe in its 3x3 it crafts it.  
Overrides comparators so they indicate number of filled slots instead  
Also makes hoppers, droppers and dispensers input max 1 item per slot  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `CREATIVE`, `EXTRAS`, `DISPENSER`  
  
## blockStateSyncing
Fixes block states in F3 debug mode not updating for some blocks. May cause increased network traffic  
Works with cactus, sugar cane, saplings, hoppers, dispensers and droppers.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## chickenShearing
Chickens can be sheared to get feathers. Beware! every time u shear a chicken, it gets damaged!  
Baby chickens can't be sheared  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  
  
## clericsFarmWarts
Clerics can warm nether farts  
This will also make them pick up wart items  
As well as pathfind to soulsand  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  
  
## commandPing
Enables /ping for players to get their ping  
* Type: `boolean`  
* Default value: `true`  
* Required options: `true`, `false`  
* Categories: `COMMAND`, `EXTRAS`  
* Additional notes:  
  * It has an accompanying command  
  
## comparatorReadsClock
Allows Comparators to read the daytime instead of the rotation of clocks in item frames  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`, `EXPERIMENTAL`  
  
## disablePlayerCollision
Disables player entity collision  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `CREATIVE`, `EXPERIMENTAL`  
  
## dispenserPlacesBlocks
Dispensers can place blocks  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `CREATIVE`, `EXTRAS`, `DISPENSER`  
  
## dispensersFeedAnimals
Dispensers can feed animals  
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
Dispensers with empty buckets can milk cows  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXPERIMENTAL`, `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersPlayRecords
Dispensers can play records if there's a jukebox in front of them  
If record already exists in the jukebox, it gets placed back in the dispenser  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersTillSoil
Dispensers with hoes can till soil  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `DISPENSER`  
  
## dispensersToggleThings
Dispensers can toggle with a stick things like buttons, doors, repeaters  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`, `DISPENSER`  
  
## doubleRetraction
1.8 double retraction from pistons  
Gives pistons the ability to double retract without side effects  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## dragonEggBedrockBreaking
Reintroduce Dragon Egg Bedrock breaking bug from 1.12  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## fireChargeConvertsToNetherrack
Fire charges from dispensers convert cobblestone to netherrack  
Credits: Skyrising  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  
  
## flowerPotChunkLoading
Place a wither rose in a flowerpot to load that chunk  
If u enable the rule the already existing chunks with flowerpots won't be loaded. Also disabling the carpet rule won't remove the loaded chunks, u need to manually unload them using the /forceload command. All the loaded chunks can be seen using `/forceload query`  
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
Allows Hopper Minecarts to transfer items out.  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `BUGFIX`, `FEATURE`, `EXTRAS`, `EXPERIMENTAL`  
  
## mobInFireConvertsSandToSoulsand
If a living entity dies on sand with fire on top the sand will convert into soul sand  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`, `EXPERIMENTAL`  
  
## reloadSuffocationFix
Won't let mobs glitch into blocks when reloaded.  
Can cause slight differences in mobs behaviour. Fixes MC-2025  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `BUGFIX`, `EXPERIMENTAL`  
  
## renewableLava
Obsidian surrounded by 6 lava sources has a chance of converting to lava  
Credits: Skyrising  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`  
  
## renewablePackedIce
Multiple ice crushed by falling anvils make packed ice  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`  
  
## renewableSand
Cobblestone crushed by falling anvils makes sand  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`  
  
## repeaterPriorityFix
Quick pulses won't get lost in repeater setups  
Probably brings back pre 1.8 behaviour. Fixes MC-54711  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `BUGFIX`, `EXPERIMENTAL`  
  
## scaffoldingDistance
Max distance for scaffolding  
* Type: `int`  
* Default value: `7`  
* Suggested options: `2`, `3`, `5`, `7`  
* Categories: `FEATURE`, `EXTRAS`  
* Additional notes:  
  * You must choose a value from 0 to 7  
  
## spongesDryInTheNether
Wet sponges dry in the nether dimension  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  

## straySpawningInIgloos
Only strays spawn in igloos 
* Type: `boolean`   
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `FEATURE`, `EXTRAS`  
  
## variableWoodDelays
variable delays on wooden components  
buttons, pressure plates  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `FEATURE`  
 