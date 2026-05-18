package carpetextra.mixins;

import java.util.Optional;
import net.minecraft.world.entity.animal.cow.MushroomCow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MushroomCow.class)
public interface MooshroomEntity_StatusEffectAccessorMixin {
    @Accessor("stewEffects")
    SuspiciousStewEffects getStewEffects();

    @Accessor("stewEffects")
    void setStewEffects(SuspiciousStewEffects effect);

    @Invoker("getEffectsFromItemStack")
    Optional<SuspiciousStewEffects> invokeGetStewEffectFrom(ItemStack flower);
}
