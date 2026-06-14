package carpetextra.dispenser.behaviors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class CauldronFillingDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        Item item = stack.getItem();
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        if(frontBlock instanceof AbstractCauldronBlock) {
            // lava
            if(item == Items.LAVA_BUCKET) {
                BlockState cauldronState = Blocks.LAVA_CAULDRON.defaultBlockState();
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.BUCKET_EMPTY_LAVA);
                return new ItemStack(Items.BUCKET);
            }
            // water
            else if(item == Items.WATER_BUCKET) {
                BlockState cauldronState = Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.BUCKET_EMPTY);
                return new ItemStack(Items.BUCKET);
            }
            // powder snow
            else if(item == Items.POWDER_SNOW_BUCKET) {
                BlockState cauldronState = Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.BUCKET_EMPTY_POWDER_SNOW);
                return new ItemStack(Items.BUCKET);
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // set cauldron, play sound, emit game event
    private static void setCauldron(ServerLevel world, BlockPos pos, BlockState state, SoundEvent soundEvent) {
        world.setBlockAndUpdate(pos, state);
        world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(null, GameEvent.FLUID_PLACE, pos);
    }
}
