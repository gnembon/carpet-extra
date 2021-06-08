package carpetextra.dispenser.behaviors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;

public class MusicDiscDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.getWorld();
        BlockPos frontBlockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();
        BlockEntity frontBlockEntity = world.getBlockEntity(frontBlockPos);

        if(frontBlock == Blocks.JUKEBOX && frontBlockEntity instanceof JukeboxBlockEntity) {
            // get record in jukebox
            ItemStack jukeboxItem = ((JukeboxBlockEntity) frontBlockEntity).getRecord();

            // set record
            ((JukeboxBlock) frontBlock).setRecord(world, frontBlockPos, frontBlockState, stack);
            // play music
            world.syncWorldEvent(1010, frontBlockPos, Item.getRawId(stack.getItem()));

            // return item that was in jukebox
            return jukeboxItem;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
