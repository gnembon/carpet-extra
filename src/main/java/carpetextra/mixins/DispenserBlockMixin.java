package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.helpers.CarpetDispenserBehaviours.*;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin
{
    private static final Item[] FEEDABLEITEMS = new Item[] { Items.GOLDEN_APPLE, Items.GOLDEN_CARROT, Items.SWEET_BERRIES, Items.SEAGRASS,
    Items.DANDELION, Items.HAY_BLOCK, Items.WHEAT, Items.CARROT, Items.POTATO, Items.BEETROOT, Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS,
    Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.COD, Items.SALMON, Items.ROTTEN_FLESH, Items.PORKCHOP, Items.CHICKEN, Items.RABBIT,
    Items.BEEF, Items.MUTTON, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT };
    private static final Set<Item> ANIMALFEEDABLE = new HashSet<Item>(Arrays.asList(FEEDABLEITEMS));
    @Shadow @Final private static Map<Item, DispenserBehavior> BEHAVIORS;

    @Inject(method = "getBehaviorForItem", at = @At("HEAD"), cancellable = true)
    private void getBehaviorForItem(ItemStack itemStack_1, CallbackInfoReturnable<DispenserBehavior> cir)
    {
        Item item = itemStack_1.getItem();
        if (CarpetExtraSettings.dispenserPlacesBlocks && !BEHAVIORS.containsKey(item) && item instanceof BlockItem)
        {
            if (PlaceBlockDispenserBehavior.canPlace(((BlockItem) item).getBlock()))
            {
                cir.setReturnValue(PlaceBlockDispenserBehavior.getInstance());
                cir.cancel();
            }
        }
        if (item == Items.GLASS_BOTTLE)
            cir.setReturnValue(new WaterBottleDispenserBehaviour());
        
        if (item == Items.CHEST)
            cir.setReturnValue(new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.CHEST));
        
        if (item == Items.HOPPER)
            cir.setReturnValue(new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.HOPPER));
        
        if (item == Items.FURNACE)
            cir.setReturnValue(new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.FURNACE));
        
        if (item == Items.TNT)
            cir.setReturnValue(new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.TNT));
        
        if (item instanceof MusicDiscItem)
            cir.setReturnValue(new DispenserRecords());
        
        if (item instanceof HoeItem)
            cir.setReturnValue(new TillSoilDispenserBehaviour());
        
        if (ANIMALFEEDABLE.contains(item.asItem()))
            cir.setReturnValue(new FeedAnimalDispenserBehaviour());
    }
}
