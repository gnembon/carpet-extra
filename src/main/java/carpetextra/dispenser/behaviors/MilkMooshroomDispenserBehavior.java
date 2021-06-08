package carpetextra.dispenser.behaviors;

import java.util.List;

import carpetextra.dispenser.DispenserBehaviorHelper;
import carpetextra.mixins.MooshroomEntity_StatusEffectAccessorMixin;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
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
            // get mushroom/suspicious stew
            ItemStack stewStack = getStewType(mooshrooms);

            // get milking sound effect depending if stew is sus
            SoundEvent stewMilkSound = stewStack.getItem() == Items.SUSPICIOUS_STEW ? SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK : SoundEvents.ENTITY_MOOSHROOM_MILK;

            // play sound
            world.playSound(null, frontBlockPos, stewMilkSound, SoundCategory.NEUTRAL, 1.0F, 1.0F);

            // add or dispense stew
            return this.addOrDispense(pointer, stack, stewStack);
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    private static ItemStack getStewType(List<MooshroomEntity> mooshrooms) {
        // check each mooshroom for stew effect, return suspicious stew of that type if exists
        for(MooshroomEntity mooshroom : mooshrooms) {
            MooshroomEntity_StatusEffectAccessorMixin mooshroomAccessor = (MooshroomEntity_StatusEffectAccessorMixin) mooshroom;
            StatusEffect stewEffect = mooshroomAccessor.getStewEffect();
            if(stewEffect != null) {
                // create suspicious stew and add mooshroom's stew effect to it
                ItemStack stewStack = new ItemStack(Items.SUSPICIOUS_STEW);
                SuspiciousStewItem.addEffectToStew(stewStack, stewEffect, mooshroomAccessor.getStewEffectDuration());

                // clear mooshroom's stew effect
                mooshroomAccessor.setStatusEffect(null);
                mooshroomAccessor.setStewEffectDuration(0);

                return stewStack;
            }
        }

        // return regular mushroom stew if no stew effects exist
        return new ItemStack(Items.MUSHROOM_STEW);
    }
}
