package carpetextra.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.ItemStack;


@Mixin(MooshroomEntity.class)
public interface MooshroomEntity_StatusEffectAccessorMixin {
    @Accessor("stewEffects")
    SuspiciousStewEffectsComponent getStewEffects();

    @Accessor("stewEffects")
    void setStewEffects(SuspiciousStewEffectsComponent effect);

    @Invoker("getStewEffectFrom")
    Optional<SuspiciousStewEffectsComponent> invokeGetStewEffectFrom(ItemStack flower);
}
