package carpetextra.dispenser.behaviors;

import java.util.List;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class FeedAnimalDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
        Box frontBlockBox = new Box(frontBlockPos);

        // get all animals in front of dispenser that are able to be fed current item and can breed or grow up
        List<AnimalEntity> animals = world.getEntitiesByClass(AnimalEntity.class, frontBlockBox, EntityPredicates.VALID_LIVING_ENTITY.and((animalEntity) -> {
            AnimalEntity animal = (AnimalEntity) animalEntity;
            return animal.isBreedingItem(stack) && animal.getBreedingAge() <= 0 && animal.canEat();
        }));

        if(!animals.isEmpty()) {
            ItemStack fedStack = tryFeed(animals, stack);
            if(fedStack != null) {
                return fedStack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    private static ItemStack tryFeed(List<AnimalEntity> animals, ItemStack foodStack) {
        // try to feed all adult animals first
        for(AnimalEntity animal : animals) {
            // check if breeding age is 0 (is adult and can breed)
            if(animal.getBreedingAge() == 0) {
                // check if animal can enter love mode with item
                if(canLoveWithItem(animal, foodStack)) {
                    animal.lovePlayer(null);
                }

                // eat item
                return eatItem(animal, foodStack);
            }
        }
        // try to grow up baby animals next
        for(AnimalEntity animal : animals) {
            if(animal.isBaby()) {
                // grow up baby animal slightly
                animal.growUp((int)(-animal.getBreedingAge() / 200), true);
                // spawn growth sparkle particle
                animal.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, animal.getParticleX(1.0D), animal.getRandomBodyY() + 0.5D, animal.getParticleZ(1.0D), 0.0D, 0.0D, 0.0D);

                // eat item
                return eatItem(animal, foodStack);
            }
        }

        // no animal can be fed
        return null;
    }

    // handles special cases for animals eating items
    // returns food item stack after being eaten
    private static ItemStack eatItem(AnimalEntity animal, ItemStack foodStack) {
        EntityType<?> type = animal.getType();

        // axolotl returns water bucket if fed tropical fish bucket
        if(type == EntityType.AXOLOTL && foodStack.getItem() == Items.TROPICAL_FISH_BUCKET) {
            return new ItemStack(Items.WATER_BUCKET);
        }

        // cats and foxes play a sound when being fed
        if(type == EntityType.CAT) {
            animal.playSound(SoundEvents.ENTITY_CAT_EAT, 1.0F, 1.0F);
        }
        else if(type == EntityType.FOX) {
            animal.playSound(animal.getEatSound(foodStack), 1.0F, 1.0F);
        }

        // remove one item and return
        foodStack.decrement(1);
        return foodStack;
    }

    // checks special cases if animal can enter love mode with item
    private static boolean canLoveWithItem(AnimalEntity animal, ItemStack foodStack) {
        EntityType<?> type = animal.getType();
        Item item = foodStack.getItem();

        // llamas only breed with hay bales
        if((type == EntityType.LLAMA || type == EntityType.TRADER_LLAMA) && item != Items.HAY_BLOCK) {
            return false;
        }
        // horses/donkeys/mules only breed with golden carrot, golden apple, or enchanted golden apple
        else if((type == EntityType.HORSE || type == EntityType.DONKEY || type == EntityType.MULE) && !(item == Items.GOLDEN_CARROT || item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE)) {
            return false;
        }

        return true;
    }
}
