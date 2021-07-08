package carpetextra.dispenser.behaviors;

import carpetextra.helpers.FlowerPotHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

public class FlowerPotDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        Item item = stack.getItem();
        ServerWorld world = pointer.getWorld();
        BlockPos frontBlockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        FlowerPotBlock frontBlock = (FlowerPotBlock) frontBlockState.getBlock();

        // check if flower pot is empty
        if(frontBlock.getContent() == Blocks.AIR && FlowerPotHelper.isPottable(item)) {
            FlowerPotBlock pottedBlock = FlowerPotHelper.getPottedBlock(item);

            // place filled flower pot
            world.setBlockState(frontBlockPos, pottedBlock.getDefaultState());
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, frontBlockPos);

            // check if flower pot should load chunk
            FlowerPotHelper.updateLoadStatus(world, frontBlockPos, pottedBlock.getContent(), true);

            // remove flower and return
            stack.decrement(1);
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
