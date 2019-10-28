# carpet-extra
Extra Features for Carpet Mod

Use along side base fabric-carpet mod for the same minecraft version

# Carpet Extra features
## accurateBlockPlacement
Client can provide alternative block placement
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `EXTRAS`, `SURVIVAL`

## autoCraftingTable
Auto-crafting table
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `CREATIVE`, `EXTRAS`

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
* Categories: `CREATIVE`, `EXTRAS`

## dispensersFeedAnimals
Dispensers can feed animals  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`  

## dispensersFillBottles
Water bottles in dispensers fill with water when dispensed with water in front.
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `EXPERIMENTAL`, `FEATURE`, `EXTRAS`

## dispensersFillMinecarts
Minecarts can be filled with hoppers, chests, tnt and furnace.
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `EXPERIMENTAL`, `FEATURE`, `EXTRAS`

## dispensersPlayRecords
Dispensers can play records if there's a jukebox in front of them
If record already exists in the jukebox, it gets placed back in the dispenser
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `EXTRAS`, `FEATURE`

## dispensersTillSoil
Dispensers with hoes can till soil
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `EXTRAS`, `FEATURE`

## dispensersToggleThings
Dispensers can toggle with a stick things like buttons, doors, repeaters
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `EXTRAS`, `EXPERIMENTAL`, `FEATURE`

## doubleRetraction
1.8 double retraction from pistons
Gives pistons the ability to double retract without side effects
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `EXTRAS`, `EXPERIMENTAL`

## dragonEggBedrockBreaking
Reintroduce Dragon Egg Bedrock breaking  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  

## fireChargeConvertsToNetherrack
Fire charges from dispensers convert cobblestone to netherrack  
Credits: Skyrising (Quickcarpet)  
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `EXTRAS`, `EXPERIMENTAL`  

## flowerPotChunkLoading
Place a wither rose in a flowerpot to load that chunk  
If u enable the rule the already existing chunks with flowerpots won't be loaded. 
Also disabling the carpet rule won't remove the loaded chunks, u need to manually unload them using the /forceload command. 
All the loaded chunks can be seen using `/forceload query`  
* Type: `boolean`  
* Default value: `false`  
* Default value: `false`  
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
Credits: Skyrising (Quickcarpet)  
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

## spongesDryInTheNether
Wet sponges dry in the nether dimension
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `EXTRAS`, `FEATURE`

## variableWoodDelays
variable delays on wooden components
buttons, pressure plates
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `EXTRAS`, `FEATURE`