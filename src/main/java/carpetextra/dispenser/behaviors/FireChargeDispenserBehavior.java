package carpetextra.dispenser.behaviors;

<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

public class FireChargeDispenserBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        BlockPos frontBlockPos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
=======
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class FireChargeDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        Block frontBlock = world.getBlockState(frontBlockPos).getBlock();

        // check if cobble, place netherrack
        if(frontBlock == Blocks.COBBLESTONE) {
<<<<<<< HEAD
            world.setBlockAndUpdate(frontBlockPos, Blocks.NETHERRACK.defaultBlockState());

            // play fire charge use sound
            world.playSound(null, frontBlockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);

            // spawn some flame particles around netherrack
            Vec3 center = Vec3.atCenterOf(frontBlockPos);
            world.sendParticles(ParticleTypes.FLAME, center.x(), center.y(), center.z(), 10, 0.5, 0.5, 0.5, 0.01);

            // decrement fire charge and return
            stack.shrink(1);
=======
            world.setBlockState(frontBlockPos, Blocks.NETHERRACK.getDefaultState());

            // play fire charge use sound
            world.playSound(null, frontBlockPos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);

            // spawn some flame particles around netherrack
            Vec3d center = Vec3d.ofCenter(frontBlockPos);
            world.spawnParticles(ParticleTypes.FLAME, center.getX(), center.getY(), center.getZ(), 10, 0.5, 0.5, 0.5, 0.01);

            // decrement fire charge and return
            stack.decrement(1);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
