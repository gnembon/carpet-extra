package carpetextra.mixins;

import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

@Mixin(VillagerProfession.class)
public abstract class VillagerProfession_wartFarmMixin
{
    @Shadow
    private static VillagerProfession register(
            Registry<VillagerProfession> registry,
            ResourceKey<VillagerProfession> key,
            Predicate<Holder<PoiType>> heldWorkstation,
            Predicate<Holder<PoiType>> acquirableWorkstation,
            ImmutableSet<Item> gatherableItems,
            ImmutableSet<Block> secondaryJobSites,
            @Nullable SoundEvent workSound
    ) {
        throw new AssertionError();
    }
    private static boolean handled = false;

    @Inject(method = "register(Lnet/minecraft/core/Registry;Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Predicate;Ljava/util/function/Predicate;Lcom/google/common/collect/ImmutableSet;Lcom/google/common/collect/ImmutableSet;Lnet/minecraft/sounds/SoundEvent;)Lnet/minecraft/world/entity/npc/VillagerProfession;", cancellable = true, at = @At("HEAD"))
    private static void registerCleric(Registry<VillagerProfession> registry, ResourceKey<VillagerProfession> key, Predicate<Holder<PoiType>> heldWorkstation, Predicate<Holder<PoiType>> acquirableWorkstation, ImmutableSet<Item> gatherableItems, ImmutableSet<Block> secondaryJobSites, @Nullable SoundEvent workSound, CallbackInfoReturnable<VillagerProfession> cir)
    {
        if (key == VillagerProfession.CLERIC && !handled)
        {
            handled = true; // recursion otherwise. Probably should just be a redirect but let's see if this works. Or even better redirect the accessors/accesses so other mod code doesn't break
            cir.setReturnValue(register(registry, key, heldWorkstation, acquirableWorkstation, gatherableItems, ImmutableSet.of(Blocks.SOUL_SAND), workSound));
        }
    }
}
