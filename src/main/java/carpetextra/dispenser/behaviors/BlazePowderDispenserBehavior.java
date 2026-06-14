package carpetextra.dispenser.behaviors;

<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlazePowderDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
=======
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;

public class BlazePowderDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        if(frontBlock == Blocks.NETHER_WART) {
<<<<<<< HEAD
            int age = frontBlockState.getValue(NetherWartBlock.AGE);
            if(age < 3) {
                // grow netherwart one stage
                world.setBlock(frontBlockPos, frontBlockState.setValue(NetherWartBlock.AGE, age + 1), Block.UPDATE_CLIENTS);
                // green sparkles
                world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, frontBlockPos, 0);

                // decrement item and return
                stack.shrink(1);
=======
            int age = frontBlockState.get(NetherWartBlock.AGE);
            if(age < 3) {
                // grow netherwart one stage
                world.setBlockState(frontBlockPos, frontBlockState.with(NetherWartBlock.AGE, age + 1), Block.NOTIFY_LISTENERS);
                // green sparkles
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, frontBlockPos, 0);

                // decrement item and return
                stack.decrement(1);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
