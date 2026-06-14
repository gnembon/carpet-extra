package carpetextra.dispenser;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class DispenserItemUsageContext extends UseOnContext {
    public DispenserItemUsageContext(Level world, ItemStack stack, BlockHitResult hit) {
        super(world, null, InteractionHand.MAIN_HAND, stack, hit);
    }
}
