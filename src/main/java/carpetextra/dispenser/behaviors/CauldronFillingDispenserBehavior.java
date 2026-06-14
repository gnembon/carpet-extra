package carpetextra.dispenser.behaviors;

<<<<<<< HEAD
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
=======
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
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        if(frontBlock instanceof AbstractCauldronBlock) {
            // lava
            if(item == Items.LAVA_BUCKET) {
<<<<<<< HEAD
                BlockState cauldronState = Blocks.LAVA_CAULDRON.defaultBlockState();
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.BUCKET_EMPTY_LAVA);
=======
                BlockState cauldronState = Blocks.LAVA_CAULDRON.getDefaultState();
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BUCKET_EMPTY_LAVA);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                return new ItemStack(Items.BUCKET);
            }
            // water
            else if(item == Items.WATER_BUCKET) {
<<<<<<< HEAD
                BlockState cauldronState = Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.BUCKET_EMPTY);
=======
                BlockState cauldronState = Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3);
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BUCKET_EMPTY);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                return new ItemStack(Items.BUCKET);
            }
            // powder snow
            else if(item == Items.POWDER_SNOW_BUCKET) {
<<<<<<< HEAD
                BlockState cauldronState = Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.BUCKET_EMPTY_POWDER_SNOW);
=======
                BlockState cauldronState = Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3);
                setCauldron(world, frontBlockPos, cauldronState, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                return new ItemStack(Items.BUCKET);
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // set cauldron, play sound, emit game event
<<<<<<< HEAD
    private static void setCauldron(ServerLevel world, BlockPos pos, BlockState state, SoundEvent soundEvent) {
        world.setBlockAndUpdate(pos, state);
        world.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(null, GameEvent.FLUID_PLACE, pos);
=======
    private static void setCauldron(ServerWorld world, BlockPos pos, BlockState state, SoundEvent soundEvent) {
        world.setBlockState(pos, state);
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
