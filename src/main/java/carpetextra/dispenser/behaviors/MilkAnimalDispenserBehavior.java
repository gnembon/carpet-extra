package carpetextra.dispenser.behaviors;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import carpetextra.dispenser.DispenserBehaviorHelper;

public class MilkAnimalDispenserBehavior extends DispenserBehaviorHelper {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));

        // check if non-baby cows/mooshrooms/goats are in front of dispenser
        List<Animal> milkableAnimals = world.getEntitiesOfClass(Animal.class, new AABB(frontBlockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((animalEntity) -> {
            return !((Animal) animalEntity).isBaby() && (animalEntity instanceof Cow || animalEntity instanceof Goat);
        }));

        if(!milkableAnimals.isEmpty()) {
            // play milking sound for a random animal in front of dispenser
            Animal milkAnimal = milkableAnimals.get(world.random.nextInt(milkableAnimals.size()));
            world.playSound(null, frontBlockPos, getMilkSound(milkAnimal), SoundSource.NEUTRAL, 1.0F, 1.0F);

            // add or dispense milk bucket stack
            return this.addOrDispense(pointer, stack, new ItemStack(Items.MILK_BUCKET));
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    private static SoundEvent getMilkSound(Animal animal) {
        if(animal.getType() == EntityType.GOAT) {
            return ((Goat) animal).isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_MILK : SoundEvents.GOAT_MILK;
        }
        return SoundEvents.COW_MILK;
    }
}
