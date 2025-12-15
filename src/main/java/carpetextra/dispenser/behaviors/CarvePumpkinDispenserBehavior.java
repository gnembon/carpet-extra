package carpetextra.dispenser.behaviors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class CarvePumpkinDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        Direction dispenserFacing = pointer.state().getValue(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().relative(dispenserFacing);
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        // check if uncarved pumpkin
        if(frontBlock == Blocks.PUMPKIN) {
            // set direction of carved pumpkin if dispenser is facing horizontal, otherwise use default
            BlockState pumpkinState = Blocks.CARVED_PUMPKIN.defaultBlockState();
            if(dispenserFacing.getAxis().isHorizontal()) {
                pumpkinState = pumpkinState.setValue(CarvedPumpkinBlock.FACING, dispenserFacing.getOpposite());
            }

            // set pumpkin, play carve sound, drop pumpkin seeds, emit game event
            world.setBlockAndUpdate(frontBlockPos, pumpkinState);
            world.playSound(null, frontBlockPos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
            Block.popResource(world, frontBlockPos, new ItemStack(Items.PUMPKIN_SEEDS, 4));
            world.gameEvent(null, GameEvent.SHEAR, frontBlockPos);

            // damage shears, remove if broken
            stack.hurtAndBreak(1, world, null, (item) -> stack.setCount(0));

            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
