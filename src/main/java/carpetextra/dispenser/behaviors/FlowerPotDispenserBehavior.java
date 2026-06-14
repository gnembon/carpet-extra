package carpetextra.dispenser.behaviors;

import carpetextra.helpers.FlowerPotHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class FlowerPotDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        Item item = stack.getItem();
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        FlowerPotBlock frontBlock = (FlowerPotBlock) frontBlockState.getBlock();

        // check if flower pot is empty
        if(frontBlock.getPotted() == Blocks.AIR && FlowerPotHelper.isPottable(item)) {
            FlowerPotBlock pottedBlock = FlowerPotHelper.getPottedBlock(item);

            // place filled flower pot
            world.setBlockAndUpdate(frontBlockPos, pottedBlock.defaultBlockState());
            world.gameEvent(null, GameEvent.BLOCK_CHANGE, frontBlockPos);

            // check if flower pot should load chunk
            FlowerPotHelper.updateLoadStatus(world, frontBlockPos, pottedBlock.getPotted(), true);

            // remove flower and return
            stack.shrink(1);
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
