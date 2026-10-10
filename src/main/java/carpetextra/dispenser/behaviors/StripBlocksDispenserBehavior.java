package carpetextra.dispenser.behaviors;

import java.util.Set;

import carpetextra.dispenser.DispenserItemUsageContext;
import carpetextra.helpers.BlockTransformHelper;
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
    public static final Set<Block> DEOXIDIZE_BLOCKS = Oxidizable.OXIDATION_LEVEL_DECREASES.get().keySet();
    public static final Set<Block> DEWAX_BLOCKS = HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().keySet();

    public static final Set<Block> DEOXIDIZE_RESULTS = Oxidizable.OXIDATION_LEVEL_DECREASES.get().values();
    public static final Set<Block> DEWAX_RESUTLS = HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().values();


    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        Direction dispenserFacing = pointer.state().get(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().offset(dispenserFacing);
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        // check if axe can be used on block
        if(BlockTransformHelper.canTransform(world, frontBlockPos, stack)) {
            BlockHitResult hitResult = new BlockHitResult(Vec3d.ofCenter(frontBlockPos), dispenserFacing.getOpposite(), frontBlockPos, false);
            ItemUsageContext context = new DispenserItemUsageContext(world, stack, hitResult);

            // use on block, test if sucessful
            if(stack.getItem().useOnBlock(context).isAccepted()) {
                // damage axe, remove if broken
                stack.damage(1, world, null, (_) -> stack.setCount(0));
                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // checks if a block is a result of being stripped with an axe
    public static boolean isStripResult(Block block) {
        return BlockTransformHelper.isStrippedBlock(block) || DEOXIDIZE_RESULTS.contains(block) || DEWAX_RESUTLS.contains(block);
    }
}
