package carpetextra.mixins;

import java.util.Optional;
<<<<<<< HEAD
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
=======

>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

<<<<<<< HEAD
@Mixin(MushroomCow.class)
public interface MooshroomEntity_StatusEffectAccessorMixin {
    @Accessor("stewEffects")
    SuspiciousStewEffects getStewEffects();

    @Accessor("stewEffects")
    void setStewEffects(SuspiciousStewEffects effect);

    @Invoker("getEffectsFromItemStack")
    Optional<SuspiciousStewEffects> invokeGetStewEffectFrom(ItemStack flower);
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
}
