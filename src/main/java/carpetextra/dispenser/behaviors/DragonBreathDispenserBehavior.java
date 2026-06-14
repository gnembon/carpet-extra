package carpetextra.dispenser.behaviors;

<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

public class DragonBreathDispenserBehavior extends OptionalDispenseItemBehavior {
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
import net.minecraft.particle.DragonBreathParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class DragonBreathDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        Block frontBlock = world.getBlockState(frontBlockPos).getBlock();

        // check if cobble, place end stone
        if(frontBlock == Blocks.COBBLESTONE) {
<<<<<<< HEAD
            world.setBlockAndUpdate(frontBlockPos, Blocks.END_STONE.defaultBlockState());

            // play dragon fireball shoot sound
            world.playSound(null, frontBlockPos, SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.BLOCKS, 1.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);

            // spawn some dragon breath particles around end stone
            Vec3 center = Vec3.atCenterOf(frontBlockPos);
            world.sendParticles(PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1), center.x(), center.y(), center.z(), 10, 0.5, 0.5, 0.5, 0.01);

            // decrement dragon breath and return
            stack.shrink(1);
=======
            world.setBlockState(frontBlockPos, Blocks.END_STONE.getDefaultState());

            // play dragon fireball shoot sound
            world.playSound(null, frontBlockPos, SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.BLOCKS, 1.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);

            // spawn some dragon breath particles around end stone
            Vec3d center = Vec3d.ofCenter(frontBlockPos);
            world.spawnParticles(DragonBreathParticleEffect.of(ParticleTypes.DRAGON_BREATH, 1), center.getX(), center.getY(), center.getZ(), 10, 0.5, 0.5, 0.5, 0.01);

            // decrement dragon breath and return
            stack.decrement(1);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
