package carpetextra.dispenser.behaviors;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

public class FeedAnimalDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        AABB frontBlockBox = new AABB(frontBlockPos);

        // get all animals in front of dispenser that are able to be fed current item and can breed or grow up
        List<Animal> animals = world.getEntitiesOfClass(Animal.class, frontBlockBox, EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((animalEntity) -> {
            Animal animal = (Animal) animalEntity;
            return animal.isFood(stack) && animal.getAge() <= 0 && animal.canFallInLove();
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

    private static ItemStack tryFeed(List<Animal> animals, ItemStack foodStack) {
        // try to feed all adult animals first
        for(Animal animal : animals) {
            // check if breeding age is 0 (is adult and can breed)
            if(animal.getAge() == 0) {
                // check if animal can enter love mode with item
                if(canLoveWithItem(animal, foodStack)) {
                    animal.setInLove(null);
                }

                // eat item
                return eatItem(animal, foodStack);
            }
        }
        // try to grow up baby animals next
        for(Animal animal : animals) {
            if(animal.isBaby()) {
                // grow up baby animal slightly
                animal.ageUp((int)(-animal.getAge() / 200), true);
                // spawn growth sparkle particle
                animal.level().addParticle(ParticleTypes.HAPPY_VILLAGER, animal.getRandomX(1.0D), animal.getRandomY() + 0.5D, animal.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);

                // eat item
                return eatItem(animal, foodStack);
            }
        }

        // no animal can be fed
        return null;
    }

    // handles special cases for animals eating items
    // returns food item stack after being eaten
    private static ItemStack eatItem(Animal animal, ItemStack foodStack) {
        EntityType<?> type = animal.getType();

        // axolotl returns water bucket if fed tropical fish bucket
        if(type == EntityType.AXOLOTL && foodStack.getItem() == Items.TROPICAL_FISH_BUCKET) {
            return new ItemStack(Items.WATER_BUCKET);
        }

        // cats and foxes play a sound when being fed
        if(type == EntityType.CAT) {
            animal.playSound(SoundEvents.CAT_EAT, 1.0F, 1.0F);
        }
        else if(type == EntityType.FOX) {
            animal.playSound(SoundEvents.FOX_EAT, 1.0F, 1.0F);
        }

        // remove one item and return
        foodStack.shrink(1);
        return foodStack;
    }

    // checks special cases if animal can enter love mode with item
    private static boolean canLoveWithItem(Animal animal, ItemStack foodStack) {
        EntityType<?> type = animal.getType();
        Item item = foodStack.getItem();

        // llamas only breed with hay bales
        if ((type == EntityType.LLAMA || type == EntityType.TRADER_LLAMA) && item != Items.HAY_BLOCK) {
            return false;
        }
        // horses/donkeys/mules only breed with golden carrot, golden apple, or enchanted golden apple
        else return (type != EntityType.HORSE && type != EntityType.DONKEY && type != EntityType.MULE) || (item == Items.GOLDEN_CARROT || item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE);
    }
}
