package carpetextra.mixins;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerProfession.class)
public abstract class VillagerProfession_wartFarmMixin
{
    @Shadow static VillagerProfession register(String string, PointOfInterestType pointOfInterestType, ImmutableSet<Item> immutableSet, ImmutableSet<Block> immutableSet2) {
        return null;
    }

    @Inject(method = "register(Ljava/lang/String;Lnet/minecraft/village/PointOfInterestType;)Lnet/minecraft/village/VillagerProfession;", cancellable = true, at = @At("HEAD"))
    private static void registerCleric(String key, PointOfInterestType pointOfInterestType, CallbackInfoReturnable<VillagerProfession> cir)
    {
        if (key.equals("cleric"))
        {
            cir.setReturnValue(register("cleric", PointOfInterestType.CLERIC, ImmutableSet.of(Items.NETHER_WART), ImmutableSet.of(Blocks.SOUL_SAND)));
        }
    }
}
