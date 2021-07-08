package carpetextra.dispenser.behaviors;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

public class CauldronFillingDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        Item item = stack.getItem();
        ServerWorld world = pointer.getWorld();
        BlockPos frontBlockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        if(frontBlock instanceof AbstractCauldronBlock) {
            // lava
            if(item == Items.LAVA_BUCKET) {
                BlockState cauldronState = Blocks.LAVA_CAULDRON.getDefaultState();
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BUCKET_EMPTY_LAVA);
                return new ItemStack(Items.BUCKET);
            }
            // water
            else if(item == Items.WATER_BUCKET) {
                BlockState cauldronState = Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3);
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BUCKET_EMPTY);
                return new ItemStack(Items.BUCKET);
            }
            // powder snow
            else if(item == Items.POWDER_SNOW_BUCKET) {
                BlockState cauldronState = Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3);
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW);
                return new ItemStack(Items.BUCKET);
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // set cauldron, play sound, emit game event
    private static void setCauldron(ServerWorld world, BlockPos pos, BlockState state, SoundEvent soundEvent) {
        world.setBlockState(pos, state);
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(GameEvent.FLUID_PLACE, pos);
    }
}
