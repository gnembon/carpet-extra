package carpetextra.dispenser;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class DispenserItemUsageContext extends ItemUsageContext {
    public DispenserItemUsageContext(World world, ItemStack stack, BlockHitResult hit) {
        super(world, null, Hand.MAIN_HAND, stack, hit);
    }
}
