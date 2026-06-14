package carpetextra.dispenser.behaviors;

import java.util.List;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

public class ShearChickenDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        AABB frontBlockBox = new AABB(frontBlockPos);

        // get adult chickens in front of dispenser
        List<Chicken> chickens = world.getEntities(EntityType.CHICKEN, frontBlockBox, EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((chickenEntity) -> !((Animal) chickenEntity).isBaby()));

        if(!chickens.isEmpty()) {
            // choose a random chicken in front of dispenser to shear
            Chicken chicken = chickens.get(world.random.nextInt(chickens.size()));

            // damage chicken, drop feather if successful
            if(chicken.hurtServer(world, world.damageSources().generic(), 1)) {
                chicken.spawnAtLocation(world, Items.FEATHER);

                // damage shears, remove if broken
                stack.hurtAndBreak(1, world, null, (item) -> stack.setCount(0));
=======

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class ShearChickenDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
        Box frontBlockBox = new Box(frontBlockPos);

        // get adult chickens in front of dispenser
        List<ChickenEntity> chickens = world.getEntitiesByType(EntityType.CHICKEN, frontBlockBox, EntityPredicates.VALID_LIVING_ENTITY.and((chickenEntity) -> !((AnimalEntity) chickenEntity).isBaby()));

        if(!chickens.isEmpty()) {
            // choose a random chicken in front of dispenser to shear
            ChickenEntity chicken = chickens.get(world.random.nextInt(chickens.size()));

            // damage chicken, drop feather if successful
            if(chicken.damage(world, world.getDamageSources().generic(), 1)) {
                chicken.dropItem(world, Items.FEATHER);

                // damage shears, remove if broken
                stack.damage(1, world, null, (item) -> stack.setCount(0));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

                // return shears
                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
