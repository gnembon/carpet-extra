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

public class FireChargeDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        BlockPos frontBlockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
        Block frontBlock = world.getBlockState(frontBlockPos).getBlock();

        // check if cobble, place netherrack
        if(frontBlock == Blocks.COBBLESTONE) {
            world.setBlockState(frontBlockPos, Blocks.NETHERRACK.getDefaultState());

            // play fire charge use sound
            world.playSound(null, frontBlockPos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);

            // spawn some flame particles around netherrack
            Vec3d center = Vec3d.ofCenter(frontBlockPos);
            world.spawnParticles(ParticleTypes.FLAME, center.getX(), center.getY(), center.getZ(), 10, 0.5, 0.5, 0.5, 0.01);

            // decrement fire charge and return
            stack.decrement(1);
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
