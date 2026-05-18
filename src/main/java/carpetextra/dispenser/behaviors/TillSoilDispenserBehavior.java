package carpetextra.dispenser.behaviors;

import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import carpetextra.dispenser.DispenserItemUsageContext;
import carpetextra.mixins.HoeItem_TilledBlocksAccessorMixin;

public class TillSoilDispenserBehavior extends OptionalDispenseItemBehavior {
    public static final Set<Block> TILLED_BLOCKS = HoeItem_TilledBlocksAccessorMixin.getTilledBlocks().keySet();

    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        Direction dispenserFacing = pointer.state().getValue(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().relative(dispenserFacing);

        // check block in front of dispenser and one block down
        for(int i = 0; i < 2; i++) {
            BlockPos hoeBlockPos = frontBlockPos.below(i);
            BlockState hoeBlockState = world.getBlockState(hoeBlockPos);
            Block hoeBlock = hoeBlockState.getBlock();

            // check if hoe can be used on block
            if(TILLED_BLOCKS.contains(hoeBlock)) {
                BlockHitResult hitResult = new BlockHitResult(Vec3.atLowerCornerOf(hoeBlockPos), dispenserFacing.getOpposite(), hoeBlockPos, false);
                UseOnContext context = new DispenserItemUsageContext(world, stack, hitResult);

                // use on block, test if successful
                if(stack.getItem().useOn(context).consumesAction()) {
                    // damage hoe, remove if broken
                    stack.hurtAndBreak(1, world, null, (item) -> stack.setCount(0));
                    return stack;
                }
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
