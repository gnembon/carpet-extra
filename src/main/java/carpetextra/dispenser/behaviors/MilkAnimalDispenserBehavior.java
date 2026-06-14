package carpetextra.dispenser.behaviors;

import java.util.List;
<<<<<<< HEAD
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
=======

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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }));

        if(!milkableAnimals.isEmpty()) {
            // play milking sound for a random animal in front of dispenser
<<<<<<< HEAD
            Animal milkAnimal = milkableAnimals.get(world.random.nextInt(milkableAnimals.size()));
            world.playSound(null, frontBlockPos, getMilkSound(milkAnimal), SoundSource.NEUTRAL, 1.0F, 1.0F);
=======
            AnimalEntity milkAnimal = milkableAnimals.get(world.random.nextInt(milkableAnimals.size()));
            world.playSound(null, frontBlockPos, getMilkSound(milkAnimal), SoundCategory.NEUTRAL, 1.0F, 1.0F);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

            // add or dispense milk bucket stack
            return this.addOrDispense(pointer, stack, new ItemStack(Items.MILK_BUCKET));
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

<<<<<<< HEAD
    private static SoundEvent getMilkSound(Animal animal) {
        if(animal.getType() == EntityType.GOAT) {
            return ((Goat) animal).isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_MILK : SoundEvents.GOAT_MILK;
        }
        return SoundEvents.COW_MILK;
=======
    private static SoundEvent getMilkSound(AnimalEntity animal) {
        if(animal.getType() == EntityType.GOAT) {
            return ((GoatEntity) animal).isScreaming() ? SoundEvents.ENTITY_GOAT_SCREAMING_MILK : SoundEvents.ENTITY_GOAT_MILK;
        }
        return SoundEvents.ENTITY_COW_MILK;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
