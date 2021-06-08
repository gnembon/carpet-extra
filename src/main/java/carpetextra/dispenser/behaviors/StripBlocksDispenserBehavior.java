package carpetextra.dispenser.behaviors;

import java.util.Collection;
import java.util.Set;

import carpetextra.dispenser.DispenserItemUsageContext;
import carpetextra.mixins.AxeItem_StrippedBlocksAccessorMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class StripBlocksDispenserBehavior extends FallibleItemDispenserBehavior {
    // stripable blocks
    public static final Set<Block> STRIPPED_BLOCKS = AxeItem_StrippedBlocksAccessorMixin.getStrippedBlocks().keySet();
    public static final Set<Block> DEOXIDIZE_BLOCKS = Oxidizable.OXIDATION_LEVEL_DECREASES.get().keySet();
    public static final Set<Block> DEWAX_BLOCKS = HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().keySet();

    // strip results
    public static final Collection<Block> STRIPPED_RESULTS = AxeItem_StrippedBlocksAccessorMixin.getStrippedBlocks().values();
    public static final Set<Block> DEOXIDIZE_RESULTS = Oxidizable.OXIDATION_LEVEL_DECREASES.get().values();
    public static final Set<Block> DEWAX_RESUTLS = HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().values();


    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.getWorld();
        Direction dispenserFacing = pointer.getBlockState().get(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.getBlockPos().offset(dispenserFacing);
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        // check if axe can be used on block
        if(canStrip(frontBlock)) {
            BlockHitResult hitResult = new BlockHitResult(Vec3d.ofCenter(frontBlockPos), dispenserFacing.getOpposite(), frontBlockPos, false);
            ItemUsageContext context = new DispenserItemUsageContext(world, stack, hitResult);

            // use on block, test if sucessful
            if(stack.getItem().useOnBlock(context).isAccepted()) {
                // damage axe, remove if broken
                if(stack.damage(1, world.random, null)) {
                    stack.setCount(0);
                }

                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // checks if a block is able to be stripped with an axe
    public static boolean canStrip(Block block) {
        return STRIPPED_BLOCKS.contains(block) || DEOXIDIZE_BLOCKS.contains(block) || DEWAX_BLOCKS.contains(block);
    }

    // checks if a block is a result of being stripped with an axe
    public static boolean isStripResult(Block block) {
        return STRIPPED_RESULTS.contains(block) || DEOXIDIZE_RESULTS.contains(block) || DEWAX_RESUTLS.contains(block);
    }
}
