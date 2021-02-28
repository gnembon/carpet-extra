package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.helpers.CarpetDispenserBehaviours.*;
import carpetextra.helpers.FeedableItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import carpetextra.utils.PlaceBlockDispenserBehavior;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin
{
    @Shadow @Final private static Map<Item, DispenserBehavior> BEHAVIORS;

    @Inject(method = "getBehaviorForItem", at = @At("HEAD"), cancellable = true)
    private void getBehaviorForItem(ItemStack itemStack_1, CallbackInfoReturnable<DispenserBehavior> cir)
    {
        Item item = itemStack_1.getItem();
        if (CarpetExtraSettings.dispensersPlaceBlocks && !BEHAVIORS.containsKey(item) && item instanceof BlockItem)
        {
            if (PlaceBlockDispenserBehavior.canPlace(((BlockItem) item).getBlock()))
            {
                cir.setReturnValue(PlaceBlockDispenserBehavior.getInstance());
                cir.cancel();
            }
        }
        //if (item == Items.GLASS_BOTTLE && CarpetExtraSettings.dispensersFillBottles) / implemented in vanilla 1.15
        //    cir.setReturnValue(new WaterBottleDispenserBehaviour());
        
        if (CarpetExtraSettings.dispensersFillMinecarts)
        {
            if (item == Items.HOPPER)
                cir.setReturnValue(new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.HOPPER));

            if (item == Items.FURNACE)
                cir.setReturnValue(new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.FURNACE));

            if (item == Items.TNT)
                cir.setReturnValue(new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.TNT));
        }

        if (CarpetExtraSettings.dispensersToggleThings && item == Items.STICK)
            cir.setReturnValue(new TogglingDispenserBehaviour());

        if (CarpetExtraSettings.dispensersPlayRecords && item instanceof MusicDiscItem)
            cir.setReturnValue(new DispenserRecords());
        
        if (CarpetExtraSettings.dispensersTillSoil && item instanceof HoeItem)
            cir.setReturnValue(new TillSoilDispenserBehaviour());

        if (CarpetExtraSettings.dispensersFeedAnimals && FeedableItems.ITEMS.contains(item.asItem()))
            cir.setReturnValue(new FeedAnimalDispenserBehaviour());
        
        if (CarpetExtraSettings.dragonsBreathConvertsCobbleToEndstone && item == Items.DRAGON_BREATH)
            cir.setReturnValue(new DragonsBreathDispenserBehaviour());
        
        if (CarpetExtraSettings.dispensersMilkCows && item == Items.BOWL)
            cir.setReturnValue(new MushroomStewBehavior());
        
        if (CarpetExtraSettings.blazeMeal && item == Items.BLAZE_POWDER)
            cir.setReturnValue(new BlazePowderBehavior());
    }
}
