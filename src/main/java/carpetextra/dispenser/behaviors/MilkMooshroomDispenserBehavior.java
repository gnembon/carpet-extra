package carpetextra.dispenser.behaviors;

import java.util.List;

import carpetextra.dispenser.DispenserBehaviorHelper;
import carpetextra.mixins.MooshroomEntity_StatusEffectAccessorMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.cow.MushroomCow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

public class MilkMooshroomDispenserBehavior extends DispenserBehaviorHelper {
    @Override
    @SuppressWarnings("resource")
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        AABB frontBlockBox = new AABB(frontBlockPos);

        // get non-baby mooshrooms in front of dispenser
        List<MushroomCow> mooshrooms = world.getEntities(EntityType.MOOSHROOM, frontBlockBox, EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((entity) -> {
            return !((MushroomCow) entity).isBaby();
        }));

        if(!mooshrooms.isEmpty()) {
            // get mushroom/suspicious stew
            ItemStack stewStack = getStewType(mooshrooms);

            // get milking sound effect depending if stew is sus
            SoundEvent stewMilkSound = stewStack.getItem() == Items.SUSPICIOUS_STEW ? SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY : SoundEvents.MOOSHROOM_MILK;

            // play sound
            world.playSound(null, frontBlockPos, stewMilkSound, SoundSource.NEUTRAL, 1.0F, 1.0F);

            // add or dispense stew
            return this.addOrDispense(pointer, stack, stewStack);
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    private static ItemStack getStewType(List<MushroomCow> mooshrooms) {
        // check each mooshroom for stew effect, return suspicious stew of that type if exists
        for (MushroomCow mooshroom : mooshrooms) {
            // from MooshroomEntity#interact
            MooshroomEntity_StatusEffectAccessorMixin mooshroomAccessor = (MooshroomEntity_StatusEffectAccessorMixin) mooshroom;
            SuspiciousStewEffects stewEffects = mooshroomAccessor.getStewEffects();
            if (stewEffects != null) {
                // create suspicious stew and add mooshroom's stew effect to it
                ItemStack stewStack = new ItemStack(Items.SUSPICIOUS_STEW);
                stewStack.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, stewEffects);

                // clear mooshroom's stew effect
                mooshroomAccessor.setStewEffects(null);

                return stewStack;
            }
        }

        // return regular mushroom stew if no stew effects exist
        return new ItemStack(Items.MUSHROOM_STEW);
    }
}
