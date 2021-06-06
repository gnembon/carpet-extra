package carpetextra.dispenser.behaviors;

import java.util.Set;

import carpetextra.dispenser.DispenserItemUsageContext;
import carpetextra.mixins.HoeItem_TilledBlocksAccessorMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class TillSoilDispenserBehavior extends FallibleItemDispenserBehavior {
    public static final Set<Block> TILLED_BLOCKS = HoeItem_TilledBlocksAccessorMixin.getTilledBlocks().keySet();

    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.getWorld();
        Direction dispenserFacing = pointer.getBlockState().get(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.getBlockPos().offset(dispenserFacing);

        // check block in front of dispenser and one block down
        for(int i = 0; i < 2; i++) {
            BlockPos hoeBlockPos = frontBlockPos.down(i);
            BlockState hoeBlockState = world.getBlockState(hoeBlockPos);
            Block hoeBlock = hoeBlockState.getBlock();

            // check if hoe can be used on block
            if(TILLED_BLOCKS.contains(hoeBlock)) {
                BlockHitResult hitResult = new BlockHitResult(Vec3d.of(hoeBlockPos), dispenserFacing.getOpposite(), hoeBlockPos, false);
                ItemUsageContext context = new DispenserItemUsageContext(world, stack, hitResult);

                // use on block, test if sucessful
                if(stack.getItem().useOnBlock(context).isAccepted()) {
                    // damage hoe, remove if broken
                    if(stack.damage(1, world.random, null)) {
                        stack.setCount(0);
                    }

                    return stack;
                }
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
