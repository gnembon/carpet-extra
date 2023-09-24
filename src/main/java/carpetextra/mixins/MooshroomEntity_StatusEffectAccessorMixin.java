package carpetextra.mixins;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.SuspiciousStewIngredient.StewEffect;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.ItemStack;

@Mixin(MooshroomEntity.class)
public interface MooshroomEntity_StatusEffectAccessorMixin {
    @Accessor("stewEffects")
    List<StewEffect> getStewEffects();

    @Accessor("stewEffects")
    void setStewEffects(List<StewEffect> effect);

    @Invoker("getStewEffectFrom")
    Optional<List<StewEffect>> invokeGetStewEffectFrom(ItemStack flower);
}
