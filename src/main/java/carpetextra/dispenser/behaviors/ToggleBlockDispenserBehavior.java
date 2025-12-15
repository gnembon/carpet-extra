package carpetextra.dispenser.behaviors;

import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ToggleBlockDispenserBehavior extends OptionalDispenseItemBehavior {
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
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        Direction dispenserFacing = pointer.state().getValue(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().relative(dispenserFacing);
        BlockState frontBlockState = world.getBlockState(frontBlockPos);

        // check if block can be toggled
        if (TOGGLEABLE_BLOCKS.contains(frontBlockState.getBlock())) {
            BlockHitResult hitResult = new BlockHitResult(Vec3.atLowerCornerOf(frontBlockPos), dispenserFacing.getOpposite(), frontBlockPos, false);

            // use on block, test if successful
            if (frontBlockState.useWithoutItem(world, null, hitResult).consumesAction()) {
                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
