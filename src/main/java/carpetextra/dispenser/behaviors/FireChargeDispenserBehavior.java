package carpetextra.dispenser.behaviors;

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
        Block frontBlock = world.getBlockState(frontBlockPos).getBlock();

        // check if cobble, place netherrack
        if(frontBlock == Blocks.COBBLESTONE) {
            world.setBlockAndUpdate(frontBlockPos, Blocks.NETHERRACK.defaultBlockState());

            // play fire charge use sound
            world.playSound(null, frontBlockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F);

            // spawn some flame particles around netherrack
            Vec3 center = Vec3.atCenterOf(frontBlockPos);
            world.sendParticles(ParticleTypes.FLAME, center.x(), center.y(), center.z(), 10, 0.5, 0.5, 0.5, 0.01);

            // decrement fire charge and return
            stack.shrink(1);
            return stack;
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
