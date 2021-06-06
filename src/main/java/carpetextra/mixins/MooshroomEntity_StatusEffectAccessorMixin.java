package carpetextra.mixins;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.ItemStack;

@Mixin(MooshroomEntity.class)
public interface MooshroomEntity_StatusEffectAccessorMixin {
    @Accessor("stewEffect")
    StatusEffect getStewEffect();

    @Accessor("stewEffect")
    void setStatusEffect(StatusEffect effect);

    @Accessor("stewEffectDuration")
    int getStewEffectDuration();

    @Accessor("stewEffectDuration")
    void setStewEffectDuration(int duration);

    @Invoker("getStewEffectFrom")
    Optional<Pair<StatusEffect, Integer>> invokeGetStewEffectFrom(ItemStack flower);
}
