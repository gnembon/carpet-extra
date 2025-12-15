package carpetextra.dispenser.behaviors;

import java.util.List;
import java.util.Optional;

import net.minecraft.tags.ItemTags;

import carpetextra.mixins.MooshroomEntity_StatusEffectAccessorMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.cow.MushroomCow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

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
                MooshroomEntity_StatusEffectAccessorMixin mooshroomAccessor = (MooshroomEntity_StatusEffectAccessorMixin) mooshroom;

                // check if mooshroom has no stew effect
                if (mooshroomAccessor.getStewEffects() == null) {
                    // get stew effect and length for flower
                	Optional<SuspiciousStewEffects> effect = mooshroomAccessor.invokeGetStewEffectFrom(stack);

                    // check if effect is present
                    if (effect.isPresent()) {

                        // set stew effect for mooshroom
                        mooshroomAccessor.setStewEffects(effect.get());

                        // play sound effect and show particles
                        world.playSound(null, frontBlockPos, SoundEvents.MOOSHROOM_EAT, SoundSource.NEUTRAL, 2.0F, 1.0F);
                        SpellParticleOption effectParticleEffect = SpellParticleOption.create(ParticleTypes.EFFECT, -1, 1.0F);
                        for (int j = 0; j < 4; j++) {
                            world.sendParticles(
                                    effectParticleEffect,
                                    mooshroom.getX() + mooshroom.getRandom().nextDouble() / 2.0,
                                    mooshroom.getY(0.5),
                                    mooshroom.getZ() + mooshroom.getRandom().nextDouble() / 2.0,
                                    1,
                                    0.0,
                                    mooshroom.getRandom().nextDouble() / 5.0,
                                    0.0,
                                    0.1D
                                );
                        }

                        // remove a flower and return stack
                        stack.shrink(1);
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
