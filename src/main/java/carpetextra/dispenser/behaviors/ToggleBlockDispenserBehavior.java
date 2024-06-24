package carpetextra.dispenser.behaviors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Set;

public class ToggleBlockDispenserBehavior extends FallibleItemDispenserBehavior {
    public static final Set<Block> TOGGLEABLE_BLOCKS = Set.of(
        Blocks.OAK_BUTTON,
        Blocks.SPRUCE_BUTTON,
        Blocks.BIRCH_BUTTON,
        Blocks.JUNGLE_BUTTON,
        Blocks.ACACIA_BUTTON,
        Blocks.DARK_OAK_BUTTON,
        Blocks.CRIMSON_BUTTON,
        Blocks.WARPED_BUTTON,
        Blocks.STONE_BUTTON,
        Blocks.MANGROVE_BUTTON,
        Blocks.BAMBOO_BUTTON,
        Blocks.CHERRY_BUTTON,
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
        ServerWorld world = pointer.world();
        Direction dispenserFacing = pointer.state().get(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().offset(dispenserFacing);
        BlockState frontBlockState = world.getBlockState(frontBlockPos);

        // check if block can be toggled
        if (TOGGLEABLE_BLOCKS.contains(frontBlockState.getBlock())) {
            BlockHitResult hitResult = new BlockHitResult(Vec3d.of(frontBlockPos), dispenserFacing.getOpposite(), frontBlockPos, false);

            // use on block, test if successful
            if (frontBlockState.onUse(world, null, hitResult).isAccepted()) {
                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
