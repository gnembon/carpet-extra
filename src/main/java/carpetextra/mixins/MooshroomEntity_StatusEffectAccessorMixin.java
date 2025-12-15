package carpetextra.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.animal.cow.MushroomCow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;

@Mixin(MushroomCow.class)
public interface MooshroomEntity_StatusEffectAccessorMixin {
    @Accessor("stewEffects")
    SuspiciousStewEffects getStewEffects();

    @Accessor("stewEffects")
    void setStewEffects(SuspiciousStewEffects effect);

    @Invoker("getEffectsFromItemStack")
    Optional<SuspiciousStewEffects> invokeGetStewEffectFrom(ItemStack flower);
}
