package carpetextra.dispenser.behaviors;

import carpetextra.helpers.FlowerPotHelper;
<<<<<<< HEAD
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
=======
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
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        FlowerPotBlock frontBlock = (FlowerPotBlock) frontBlockState.getBlock();

        // check if flower pot is empty
<<<<<<< HEAD
        if(frontBlock.getPotted() == Blocks.AIR && FlowerPotHelper.isPottable(item)) {
            FlowerPotBlock pottedBlock = FlowerPotHelper.getPottedBlock(item);

            // place filled flower pot
            world.setBlockAndUpdate(frontBlockPos, pottedBlock.defaultBlockState());
            world.gameEvent(null, GameEvent.BLOCK_CHANGE, frontBlockPos);

            // check if flower pot should load chunk
            FlowerPotHelper.updateLoadStatus(world, frontBlockPos, pottedBlock.getPotted(), true);

            // remove flower and return
            stack.shrink(1);
=======
        if(frontBlock.getContent() == Blocks.AIR && FlowerPotHelper.isPottable(item)) {
            FlowerPotBlock pottedBlock = FlowerPotHelper.getPottedBlock(item);

            // place filled flower pot
            world.setBlockState(frontBlockPos, pottedBlock.getDefaultState());
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, frontBlockPos);

            // check if flower pot should load chunk
            FlowerPotHelper.updateLoadStatus(world, frontBlockPos, pottedBlock.getContent(), true);

            // remove flower and return
            stack.decrement(1);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
