package carpetextra.dispenser.behaviors;

<<<<<<< HEAD
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
=======
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;

public class CarvePumpkinDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        Direction dispenserFacing = pointer.state().get(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().offset(dispenserFacing);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        // check if uncarved pumpkin
        if(frontBlock == Blocks.PUMPKIN) {
            // set direction of carved pumpkin if dispenser is facing horizontal, otherwise use default
<<<<<<< HEAD
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
=======
            BlockState pumpkinState = Blocks.CARVED_PUMPKIN.getDefaultState();
            if(dispenserFacing.getAxis().isHorizontal()) {
                pumpkinState = pumpkinState.with(CarvedPumpkinBlock.FACING, dispenserFacing.getOpposite());
            }

            // set pumpkin, play carve sound, drop pumpkin seeds, emit game event
            world.setBlockState(frontBlockPos, pumpkinState);
            world.playSound(null, frontBlockPos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            Block.dropStack(world, frontBlockPos, new ItemStack(Items.PUMPKIN_SEEDS, 4));
            world.emitGameEvent(null, GameEvent.SHEAR, frontBlockPos);

            // damage shears, remove if broken
            stack.damage(1, world, null, (item) -> stack.setCount(0));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
