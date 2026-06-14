package carpetextra.dispenser.behaviors;

import java.util.List;
import java.util.Optional;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import carpetextra.mixins.MooshroomEntity_StatusEffectAccessorMixin;

public class FeedMooshroomDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));

        // check if item is in SMALL_FLOWERS item tag
        if(stack.is(ItemTags.SMALL_FLOWERS))
        {
            // get brown mooshrooms in front of dispenser
            List<MushroomCow> mooshrooms = world.getEntities(EntityType.MOOSHROOM, new AABB(frontBlockPos), EntitySelector.LIVING_ENTITY_STILL_ALIVE.and((mooshroom) -> {
                return ((MushroomCow) mooshroom).getVariant() == MushroomCow.Variant.BROWN;
            }));

            // check all mooshrooms
            for (MushroomCow mooshroom : mooshrooms) {
=======

import net.minecraft.registry.tag.ItemTags;

import carpetextra.mixins.MooshroomEntity_StatusEffectAccessorMixin;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.EffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class FeedMooshroomDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));

        // check if item is in SMALL_FLOWERS item tag
        if(stack.isIn(ItemTags.SMALL_FLOWERS))
        {
            // get brown mooshrooms in front of dispenser
            List<MooshroomEntity> mooshrooms = world.getEntitiesByType(EntityType.MOOSHROOM, new Box(frontBlockPos), EntityPredicates.VALID_LIVING_ENTITY.and((mooshroom) -> {
                return ((MooshroomEntity) mooshroom).getVariant() == MooshroomEntity.Variant.BROWN;
            }));

            // check all mooshrooms
            for (MooshroomEntity mooshroom : mooshrooms) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                MooshroomEntity_StatusEffectAccessorMixin mooshroomAccessor = (MooshroomEntity_StatusEffectAccessorMixin) mooshroom;

                // check if mooshroom has no stew effect
                if (mooshroomAccessor.getStewEffects() == null) {
                    // get stew effect and length for flower
<<<<<<< HEAD
                	Optional<SuspiciousStewEffects> effect = mooshroomAccessor.invokeGetStewEffectFrom(stack);
=======
                	Optional<SuspiciousStewEffectsComponent> effect = mooshroomAccessor.invokeGetStewEffectFrom(stack);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

                    // check if effect is present
                    if (effect.isPresent()) {

                        // set stew effect for mooshroom
                        mooshroomAccessor.setStewEffects(effect.get());

                        // play sound effect and show particles
<<<<<<< HEAD
                        world.playSound(null, frontBlockPos, SoundEvents.MOOSHROOM_EAT, SoundSource.NEUTRAL, 2.0F, 1.0F);
                        SpellParticleOption effectParticleEffect = SpellParticleOption.create(ParticleTypes.EFFECT, -1, 1.0F);
                        for (int j = 0; j < 4; j++) {
                            world.sendParticles(
                                    effectParticleEffect,
                                    mooshroom.getX() + mooshroom.getRandom().nextDouble() / 2.0,
                                    mooshroom.getY(0.5),
=======
                        world.playSound(null, frontBlockPos, SoundEvents.ENTITY_MOOSHROOM_EAT, SoundCategory.NEUTRAL, 2.0F, 1.0F);
                        EffectParticleEffect effectParticleEffect = EffectParticleEffect.of(ParticleTypes.EFFECT, -1, 1.0F);
                        for (int j = 0; j < 4; j++) {
                            world.spawnParticles(
                                    effectParticleEffect,
                                    mooshroom.getX() + mooshroom.getRandom().nextDouble() / 2.0,
                                    mooshroom.getBodyY(0.5),
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                                    mooshroom.getZ() + mooshroom.getRandom().nextDouble() / 2.0,
                                    1,
                                    0.0,
                                    mooshroom.getRandom().nextDouble() / 5.0,
                                    0.0,
                                    0.1D
                                );
                        }

                        // remove a flower and return stack
<<<<<<< HEAD
                        stack.shrink(1);
=======
                        stack.decrement(1);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                        return stack;
                    }
                }
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
