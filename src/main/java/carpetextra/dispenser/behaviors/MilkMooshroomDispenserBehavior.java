package carpetextra.dispenser.behaviors;

import java.util.List;

import carpetextra.dispenser.DispenserBehaviorHelper;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class MilkMooshroomDispenserBehavior extends DispenserBehaviorHelper {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.getWorld();
        BlockPos frontBlockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        Box frontBlockBox = new Box(frontBlockPos);

        // get non-baby mooshrooms in front of dispenser
        List<MooshroomEntity> mooshrooms = world.getEntitiesByType(EntityType.MOOSHROOM, frontBlockBox, EntityPredicates.VALID_LIVING_ENTITY.and((entity) -> {
            return !((MooshroomEntity) entity).isBaby();
        }));

        if(!mooshrooms.isEmpty()) {
            ItemStack stewStack = new ItemStack(Items.MUSHROOM_STEW);

            // play sound
            world.playSound(null, frontBlockPos, SoundEvents.ENTITY_MOOSHROOM_MILK, SoundCategory.NEUTRAL, 1.0F, 1.0F);

            // add or dispense stew
            return this.addOrDispense(pointer, stack, stewStack);
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
