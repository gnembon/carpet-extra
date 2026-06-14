package carpetextra.dispenser;

<<<<<<< HEAD
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class DispenserItemUsageContext extends UseOnContext {
    public DispenserItemUsageContext(Level world, ItemStack stack, BlockHitResult hit) {
        super(world, null, InteractionHand.MAIN_HAND, stack, hit);
=======
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class DispenserItemUsageContext extends ItemUsageContext {
    public DispenserItemUsageContext(World world, ItemStack stack, BlockHitResult hit) {
        super(world, null, Hand.MAIN_HAND, stack, hit);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
