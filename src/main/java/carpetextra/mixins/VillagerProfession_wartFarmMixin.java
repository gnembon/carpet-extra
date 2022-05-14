package carpetextra.mixins;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.RegistryKey;
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


    @Shadow static VillagerProfession register(String string, RegistryKey<PointOfInterestType> pointOfInterestType, ImmutableSet<Item> immutableSet, ImmutableSet<Block> immutableSet2, /*@Nullable*/ SoundEvent soundEvent) {return null;};

    @Inject(method = "register(Ljava/lang/String;Lnet/minecraft/world/poi/PointOfInterestType;Lnet/minecraft/sound/SoundEvent;)Lnet/minecraft/village/VillagerProfession;", cancellable = true, at = @At("HEAD"))
    private static void registerCleric(String key, RegistryKey<PointOfInterestType> pointOfInterestType, SoundEvent soundEvent, CallbackInfoReturnable<VillagerProfession> cir)
    {
        if (key.equals("cleric"))
        {
            cir.setReturnValue(register(key, pointOfInterestType, ImmutableSet.of(Items.NETHER_WART), ImmutableSet.of(Blocks.SOUL_SAND), soundEvent));
        }
    }
}
