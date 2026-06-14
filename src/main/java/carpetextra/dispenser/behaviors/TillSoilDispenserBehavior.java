package carpetextra.dispenser.behaviors;

import java.util.Set;
<<<<<<< HEAD
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
=======

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
        ServerWorld world = pointer.world();
        Direction dispenserFacing = pointer.state().get(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().offset(dispenserFacing);

        // check block in front of dispenser and one block down
        for(int i = 0; i < 2; i++) {
            BlockPos hoeBlockPos = frontBlockPos.down(i);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            BlockState hoeBlockState = world.getBlockState(hoeBlockPos);
            Block hoeBlock = hoeBlockState.getBlock();

            // check if hoe can be used on block
            if(TILLED_BLOCKS.contains(hoeBlock)) {
<<<<<<< HEAD
                BlockHitResult hitResult = new BlockHitResult(Vec3.atLowerCornerOf(hoeBlockPos), dispenserFacing.getOpposite(), hoeBlockPos, false);
                UseOnContext context = new DispenserItemUsageContext(world, stack, hitResult);

                // use on block, test if successful
                if(stack.getItem().useOn(context).consumesAction()) {
                    // damage hoe, remove if broken
                    stack.hurtAndBreak(1, world, null, (item) -> stack.setCount(0));
=======
                BlockHitResult hitResult = new BlockHitResult(Vec3d.of(hoeBlockPos), dispenserFacing.getOpposite(), hoeBlockPos, false);
                ItemUsageContext context = new DispenserItemUsageContext(world, stack, hitResult);

                // use on block, test if successful
                if(stack.getItem().useOnBlock(context).isAccepted()) {
                    // damage hoe, remove if broken
                    stack.damage(1, world, null, (item) -> stack.setCount(0));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    return stack;
                }
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
