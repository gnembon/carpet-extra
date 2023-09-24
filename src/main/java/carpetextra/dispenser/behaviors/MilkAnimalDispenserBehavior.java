package carpetextra.dispenser.behaviors;

import java.util.List;

import carpetextra.dispenser.DispenserBehaviorHelper;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class MilkAnimalDispenserBehavior extends DispenserBehaviorHelper {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));

        // check if non-baby cows/mooshrooms/goats are in front of dispenser
        List<AnimalEntity> milkableAnimals = world.getEntitiesByClass(AnimalEntity.class, new Box(frontBlockPos), EntityPredicates.VALID_LIVING_ENTITY.and((animalEntity) -> {
            return !((AnimalEntity) animalEntity).isBaby() && (animalEntity instanceof CowEntity || animalEntity instanceof GoatEntity);
        }));

        if(!milkableAnimals.isEmpty()) {
            // play milking sound for a random animal in front of dispenser
            AnimalEntity milkAnimal = milkableAnimals.get(world.random.nextInt(milkableAnimals.size()));
            world.playSound(null, frontBlockPos, getMilkSound(milkAnimal), SoundCategory.NEUTRAL, 1.0F, 1.0F);

            // add or dispense milk bucket stack
            return this.addOrDispense(pointer, stack, new ItemStack(Items.MILK_BUCKET));
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    private static SoundEvent getMilkSound(AnimalEntity animal) {
        if(animal.getType() == EntityType.GOAT) {
            return ((GoatEntity) animal).isScreaming() ? SoundEvents.ENTITY_GOAT_SCREAMING_MILK : SoundEvents.ENTITY_GOAT_MILK;
        }
        return SoundEvents.ENTITY_COW_MILK;
    }
}
