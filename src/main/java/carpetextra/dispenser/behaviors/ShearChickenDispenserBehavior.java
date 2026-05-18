package carpetextra.dispenser.behaviors;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
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
            Chicken chicken = chickens.get(world.getRandom().nextInt(chickens.size()));

            // damage chicken, drop feather if successful
            if(chicken.hurtServer(world, world.damageSources().generic(), 1)) {
                chicken.spawnAtLocation(world, Items.FEATHER);

                // damage shears, remove if broken
                stack.hurtAndBreak(1, world, null, (item) -> stack.setCount(0));

                // return shears
                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
