package carpetextra.mixins;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerProfession.class)
public abstract class VillagerProfession_wartFarmMixin
{
    @Shadow
    static VillagerProfession register(String id, RegistryKey<PointOfInterestType> poiType, ImmutableSet<Item> gatherableItems, ImmutableSet<Block> secondaryJobSites, /*@Nullable*/ SoundEvent soundEvent) {
        throw new AssertionError();
    };

    @Inject(method = "register(Ljava/lang/String;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/sound/SoundEvent;)Lnet/minecraft/village/VillagerProfession;", cancellable = true, at = @At("HEAD"))
    private static void registerCleric(String key, RegistryKey<PointOfInterestType> poiType, SoundEvent soundEvent, CallbackInfoReturnable<VillagerProfession> cir)
    {
        if (key.equals("cleric"))
        {
            cir.setReturnValue(register(key, poiType, ImmutableSet.of(Items.NETHER_WART), ImmutableSet.of(Blocks.SOUL_SAND), soundEvent));
        }
    }
}
