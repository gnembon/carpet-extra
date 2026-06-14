package carpetextra.dispenser.behaviors;

import carpetextra.dispenser.DispenserBehaviorHelper;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class CauldronEmptyingDispenserBehavior extends DispenserBehaviorHelper {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
=======
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

public class CauldronEmptyingDispenserBehavior extends DispenserBehaviorHelper {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        // check if front block is cauldron and is full
        if(frontBlock instanceof AbstractCauldronBlock && ((AbstractCauldronBlock) frontBlock).isFull(frontBlockState)) {
            // lava
            if(frontBlock == Blocks.LAVA_CAULDRON) {
<<<<<<< HEAD
                setCauldron(world, frontBlockPos, SoundEvents.BUCKET_FILL_LAVA);
=======
                setCauldron(world, frontBlockPos, SoundEvents.ITEM_BUCKET_FILL_LAVA);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                return this.addOrDispense(pointer, stack, new ItemStack(Items.LAVA_BUCKET));
            }
            // water
            else if(frontBlock == Blocks.WATER_CAULDRON) {
<<<<<<< HEAD
                setCauldron(world, frontBlockPos, SoundEvents.BUCKET_FILL);
=======
                setCauldron(world, frontBlockPos, SoundEvents.ITEM_BUCKET_FILL);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                return this.addOrDispense(pointer, stack, new ItemStack(Items.WATER_BUCKET));
            }
            // powder snow
            else if(frontBlock == Blocks.POWDER_SNOW_CAULDRON) {
<<<<<<< HEAD
                setCauldron(world, frontBlockPos, SoundEvents.BUCKET_FILL_POWDER_SNOW);
=======
                setCauldron(world, frontBlockPos, SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                return this.addOrDispense(pointer, stack, new ItemStack(Items.POWDER_SNOW_BUCKET));
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // set cauldron, play sound, emit game event
<<<<<<< HEAD
    private static void setCauldron(ServerLevel world, BlockPos pos, SoundEvent soundEvent) {
        world.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
        world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
=======
    private static void setCauldron(ServerWorld world, BlockPos pos, SoundEvent soundEvent) {
        world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
