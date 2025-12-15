package carpetextra.dispenser;

import java.util.Map;

import carpetextra.CarpetExtraSettings;
import carpetextra.dispenser.behaviors.BlazePowderDispenserBehavior;
import carpetextra.dispenser.behaviors.CarvePumpkinDispenserBehavior;
import carpetextra.dispenser.behaviors.CauldronEmptyingDispenserBehavior;
import carpetextra.dispenser.behaviors.CauldronFillingDispenserBehavior;
import carpetextra.dispenser.behaviors.CauldronWaterDispenserBehavior;
import carpetextra.dispenser.behaviors.DragonBreathDispenserBehavior;
import carpetextra.dispenser.behaviors.FeedAnimalDispenserBehavior;
import carpetextra.dispenser.behaviors.FeedMooshroomDispenserBehavior;
import carpetextra.dispenser.behaviors.FillMinecartDispenserBehavior;
import carpetextra.dispenser.behaviors.FireChargeDispenserBehavior;
import carpetextra.dispenser.behaviors.FlowerPotDispenserBehavior;
import carpetextra.dispenser.behaviors.MilkAnimalDispenserBehavior;
import carpetextra.dispenser.behaviors.MilkMooshroomDispenserBehavior;
import carpetextra.dispenser.behaviors.PlaceBoatOnIceDispenserBehavior;
import carpetextra.dispenser.behaviors.ShearChickenDispenserBehavior;
import carpetextra.dispenser.behaviors.StripBlocksDispenserBehavior;
import carpetextra.dispenser.behaviors.TillSoilDispenserBehavior;
import carpetextra.dispenser.behaviors.ToggleBlockDispenserBehavior;
import carpetextra.helpers.FlowerPotHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.cow.MushroomCow;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CarpetExtraDispenserBehaviors {
    // instances of custom dispenser behaviors
    // blazeMeal
    public static final DispenseItemBehavior BLAZE_MEAL = new BlazePowderDispenserBehavior();
    // chickenShearing
    public static final DispenseItemBehavior SHEAR_CHICKEN = new ShearChickenDispenserBehavior();
    // dispensersCarvePumpkins
    public static final DispenseItemBehavior CARVE_PUMPKIN = new CarvePumpkinDispenserBehavior();
    // dispensersFeedAnimals
    public static final DispenseItemBehavior FEED_ANIMAL = new FeedAnimalDispenserBehavior();
    public static final DispenseItemBehavior FEED_MOOSHROOM = new FeedMooshroomDispenserBehavior();
    // dispensersFillMinecarts
    public static final DispenseItemBehavior FILL_MINECART_CHEST = new FillMinecartDispenserBehavior(EntityType.CHEST_MINECART);
    public static final DispenseItemBehavior FILL_MINECART_FURNACE = new FillMinecartDispenserBehavior(EntityType.FURNACE_MINECART);
    public static final DispenseItemBehavior FILL_MINECART_TNT = new FillMinecartDispenserBehavior(EntityType.TNT_MINECART);
    public static final DispenseItemBehavior FILL_MINECART_HOPPER = new FillMinecartDispenserBehavior(EntityType.HOPPER_MINECART);
    // dispensersMilkAnimals
    public static final DispenseItemBehavior MILK_ANIMAL = new MilkAnimalDispenserBehavior();
    public static final DispenseItemBehavior MILK_MOOSHROOM = new MilkMooshroomDispenserBehavior();
    // dispensersPotPlants
    public static final DispenseItemBehavior FILL_FLOWER_POT = new FlowerPotDispenserBehavior();
    // dispensersStripBlocks
    public static final DispenseItemBehavior STRIP_BLOCK = new StripBlocksDispenserBehavior();
    // dispensersTillSoil
    public static final DispenseItemBehavior TILL_SOIL = new TillSoilDispenserBehavior();
    // dispensersToggleThings
    public static final DispenseItemBehavior TOGGLE_BLOCK = new ToggleBlockDispenserBehavior();
    // dispensersUseCauldrons
    public static final DispenseItemBehavior CAULDRON_FILLING_BUCKET = new CauldronFillingDispenserBehavior();
    public static final DispenseItemBehavior CAULDRON_EMPTYING_BUCKET = new CauldronEmptyingDispenserBehavior();
    public static final DispenseItemBehavior CAULDRON_WATER = new CauldronWaterDispenserBehavior();
    // renewableEndstone
    public static final DispenseItemBehavior DRAGON_BREATH_ENDSTONE = new DragonBreathDispenserBehavior();
    // renewableNetherrack
    public static final DispenseItemBehavior FIRE_CHARGE_NETHERRACK = new FireChargeDispenserBehavior();
    // dispensersPlaceBoatsOnIce
    public static final DispenseItemBehavior PLACE_BOAT_ON_ICE = new PlaceBoatOnIceDispenserBehavior();


    // get custom dispenser behavior
    // this checks conditions such as the item and certain block or entity being in front of the dispenser to decide which rule to return
    // if the conditions for the rule match, it returns the instance of the dispenser behavior
    // returns null to fallback to vanilla (or another mod's) behavior for the given item
    public static DispenseItemBehavior getCustomDispenserBehavior(ServerLevel world, BlockPos pos, BlockSource pointer, DispenserBlockEntity dispenserBlockEntity, ItemStack stack, Map<Item, DispenseItemBehavior> VANILLA_BEHAVIORS) {
        Item item = stack.getItem();
        Direction dispenserFacing = pointer.state().getValue(DispenserBlock.FACING);
        BlockPos frontBlockPos = pos.relative(dispenserFacing);
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();
        AABB frontBlockBox = new AABB(frontBlockPos);

        // blazeMeal
        if(CarpetExtraSettings.blazeMeal && item == Items.BLAZE_POWDER && frontBlock == Blocks.NETHER_WART) {
            return BLAZE_MEAL;
        }

        // chickenShearing
        if(CarpetExtraSettings.chickenShearing && item == Items.SHEARS) {
            boolean hasShearableChickens = !world.getEntities(EntityType.CHICKEN, frontBlockBox, EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((chickenEntity) ->
                                           !((Animal) chickenEntity).isBaby())).isEmpty();

            if(hasShearableChickens) {
                return SHEAR_CHICKEN;
            }
        }

        // dispensersCarvePumpkins
        if(CarpetExtraSettings.dispensersCarvePumpkins && item instanceof ShearsItem && frontBlock == Blocks.PUMPKIN) {
            return CARVE_PUMPKIN;
        }

        // dispensersFeedAnimals
        if(CarpetExtraSettings.dispensersFeedAnimals) {
            // check for animals that can be bred with the current item being dispensed in front of dispenser
            boolean hasFeedableAnimals = !world.getEntitiesOfClass(Animal.class, frontBlockBox, EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((animalEntity) ->
                                         ((Animal) animalEntity).isFood(stack))).isEmpty();

            if(hasFeedableAnimals) {
                return FEED_ANIMAL;
            }

            // get brown mooshrooms in front of dispenser
            boolean hasFeedableMooshrooms = !world.getEntities(EntityType.MOOSHROOM, frontBlockBox, EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((mooshroomEntity) ->
                                             ((MushroomCow) mooshroomEntity).getVariant() == MushroomCow.Variant.BROWN)).isEmpty();

            // check if item is a small flower
            if(hasFeedableMooshrooms && stack.is(ItemTags.SMALL_FLOWERS)) {
                return FEED_MOOSHROOM;
            }
        }

        // dispensersFillMinecarts
        if(CarpetExtraSettings.dispensersFillMinecarts) {
            // check for minecarts with no riders in front of dispenser
            boolean hasMinecarts = !world.getEntities(EntityType.MINECART, frontBlockBox, EntitySelector.ENTITY_NOT_BEING_RIDDEN).isEmpty();

            // if a minecart exist, return dispenser behavior according to item type
            if(hasMinecarts) {
                if(item == Items.CHEST) {
                    return FILL_MINECART_CHEST;
                }
                else if(item == Items.FURNACE) {
                    return FILL_MINECART_FURNACE;
                }
                else if(item == Items.TNT) {
                    return FILL_MINECART_TNT;
                }
                else if(item == Items.HOPPER) {
                    return FILL_MINECART_HOPPER;
                }
            }
        }

        // dispensersMilkAnimals
        if(CarpetExtraSettings.dispensersMilkAnimals) {
            // bucket to milk
            if(item == Items.BUCKET) {
                // check for cows, mooshrooms, or goats in front of dispenser
                boolean hasMilkable = !world.getEntitiesOfClass(Animal.class, frontBlockBox, EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((animalEntity) ->
                                      animalEntity instanceof Cow || animalEntity instanceof Goat)).isEmpty();

                if(hasMilkable) {
                    return MILK_ANIMAL;
                }
            }
            // bowl to stew
            else if(item == Items.BOWL) {
                // check for mooshrooms in front of dispenser
                boolean hasMooshroom = !world.getEntities(EntityType.MOOSHROOM, frontBlockBox, EntitySelector.LIVING_ENTITY_STILL_ALIVE).isEmpty();

                if(hasMooshroom) {
                    return MILK_MOOSHROOM;
                }
            }
        }

        // dispensersPotPlants
        if(CarpetExtraSettings.dispensersPotPlants && frontBlock instanceof FlowerPotBlock && FlowerPotHelper.isPottable(item)) {
            return FILL_FLOWER_POT;
        }

        // dispensersStripBlocks
        if(CarpetExtraSettings.dispensersStripBlocks && item instanceof AxeItem && (StripBlocksDispenserBehavior.canStrip(frontBlock) || StripBlocksDispenserBehavior.isStripResult(frontBlock))) {
            return STRIP_BLOCK;
        }

        // dispensersTillSoil
        if(CarpetExtraSettings.dispensersTillSoil && item instanceof HoeItem) {
            // check block in front of dispenser and one block down
            for(int i = 0; i < 2; i++) {
                BlockPos hoeBlockPos = frontBlockPos.below(i);
                Block hoeBlock = world.getBlockState(hoeBlockPos).getBlock();

                // check if block is in tilled blocks, or is farmland (to prevent hoe being dispensed when you don't want it to)
                if(TillSoilDispenserBehavior.TILLED_BLOCKS.contains(hoeBlock) || hoeBlock == Blocks.FARMLAND) {
                    return TILL_SOIL;
                }
            }
        }

        // dispensersToggleThings
        if(CarpetExtraSettings.dispensersToggleThings && item == Items.STICK && ToggleBlockDispenserBehavior.TOGGLEABLE_BLOCKS.contains(frontBlock)) {
            return TOGGLE_BLOCK;
        }

        // dispensersUseCauldrons
        if(CarpetExtraSettings.dispensersUseCauldrons && frontBlock instanceof AbstractCauldronBlock) {
            // empty cauldron
            if(item == Items.BUCKET) {
                return CAULDRON_EMPTYING_BUCKET;
            }
            // fill cauldron
            else if(item == Items.LAVA_BUCKET || item == Items.WATER_BUCKET || item == Items.POWDER_SNOW_BUCKET) {
                return CAULDRON_FILLING_BUCKET;
            }
            // water cauldron behaviors (leather armor, shulker boxes, banners)
            else if(CauldronWaterDispenserBehavior.isWaterCauldronItem(stack)) {
                return CAULDRON_WATER;
            }
        }

        // renewableEndstone
        if(CarpetExtraSettings.renewableEndstone && item == Items.DRAGON_BREATH && frontBlock == Blocks.COBBLESTONE) {
            return DRAGON_BREATH_ENDSTONE;
        }

        // renewableNetherrack
        if(CarpetExtraSettings.renewableNetherrack && item == Items.FIRE_CHARGE && frontBlock == Blocks.COBBLESTONE) {
            return FIRE_CHARGE_NETHERRACK;
        }

        // dispensersPlaceBoatsOnIce
        if (CarpetExtraSettings.dispensersPlaceBoatsOnIce && item instanceof BoatItem && frontBlock == Blocks.AIR) {
            BlockPos blockBelowFrontBlockPos = frontBlockPos.below();
            if (world.getBlockState(blockBelowFrontBlockPos).is(BlockTags.ICE)) {
                return PLACE_BOAT_ON_ICE;
            }
        }


        // no custom behavior, return null
        return null;
    }
}
