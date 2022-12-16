package carpetextra.dispenser.behaviors;

import java.util.Set;

import carpetextra.fakes.FakePlayerEntity;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class ToggleBlockDispenserBehavior extends FallibleItemDispenserBehavior {

    private FakePlayerEntity fakePlayerEntity;

    public static Set<Block> TOGGLEABLE_BLOCKS = Sets.newHashSet(
        Blocks.OAK_BUTTON,
        Blocks.SPRUCE_BUTTON,
        Blocks.BIRCH_BUTTON,
        Blocks.JUNGLE_BUTTON,
        Blocks.ACACIA_BUTTON,
        Blocks.DARK_OAK_BUTTON,
        Blocks.CRIMSON_BUTTON,
        Blocks.WARPED_BUTTON,
        Blocks.STONE_BUTTON,
        Blocks.POLISHED_BLACKSTONE_BUTTON,
        Blocks.LEVER,
        Blocks.REPEATER,
        Blocks.COMPARATOR,
        Blocks.DAYLIGHT_DETECTOR,
        Blocks.REDSTONE_ORE,
        Blocks.JUKEBOX,
        Blocks.NOTE_BLOCK,
        Blocks.REDSTONE_WIRE,
        Blocks.DRAGON_EGG
    );

    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.getWorld();
        Direction dispenserFacing = pointer.getBlockState().get(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.getPos().offset(dispenserFacing);
        BlockState frontBlockState = world.getBlockState(frontBlockPos);

        // check if block can be toggled
        if(TOGGLEABLE_BLOCKS.contains(frontBlockState.getBlock())) {
            BlockHitResult hitResult = new BlockHitResult(Vec3d.of(frontBlockPos), dispenserFacing.getOpposite(), frontBlockPos, false);

            // make fake player
            if (fakePlayerEntity == null) fakePlayerEntity = new FakePlayerEntity(world, pointer.getPos());
            fakePlayerEntity.setStackInHand(Hand.MAIN_HAND, stack);

            // try to use block
            if(frontBlockState.onUse(world, fakePlayerEntity, Hand.MAIN_HAND, hitResult).isAccepted()) {
                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
