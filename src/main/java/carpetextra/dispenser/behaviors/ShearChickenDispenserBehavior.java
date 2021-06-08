package carpetextra.dispenser.behaviors;

import java.util.List;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
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
        ServerWorld world = pointer.getWorld();
        BlockPos frontBlockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        Box frontBlockBox = new Box(frontBlockPos);

        // get adult chickens in front of dispenser
        List<ChickenEntity> chickens = world.getEntitiesByType(EntityType.CHICKEN, frontBlockBox, EntityPredicates.VALID_LIVING_ENTITY.and((chickenEntity) -> {
            return !((AnimalEntity) chickenEntity).isBaby();
        }));

        if(!chickens.isEmpty()) {
            // choose a random chicken in front of dispenser to shear
            ChickenEntity chicken = chickens.get(world.random.nextInt(chickens.size()));

            // damage chicken, drop feather if successful
            if(chicken.damage(DamageSource.GENERIC, 1)) {
                chicken.dropItem(Items.FEATHER);

                // damage shears, remove if broken
                if(stack.damage(1, world.random, null)) {
                    stack.setCount(0);
                }

                // return shears
                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
