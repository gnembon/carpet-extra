package carpetextra.dispenser.behaviors;

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

public class DragonBreathDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
        Block frontBlock = world.getBlockState(frontBlockPos).getBlock();

        // check if cobble, place end stone
        if(frontBlock == Blocks.COBBLESTONE) {
            world.setBlockState(frontBlockPos, Blocks.END_STONE.getDefaultState());

            // play dragon fireball shoot sound
            world.playSound(null, frontBlockPos, SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.BLOCKS, 1.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);

            // spawn some dragon breath particles around end stone
            Vec3d center = Vec3d.ofCenter(frontBlockPos);
//            TODO: Replace with actual dragons breath particle
            world.spawnParticles(ParticleTypes.EXPLOSION, center.getX(), center.getY(), center.getZ(), 10, 0.5, 0.5, 0.5, 0.01);

            // decrement dragon breath and return
            stack.decrement(1);
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
