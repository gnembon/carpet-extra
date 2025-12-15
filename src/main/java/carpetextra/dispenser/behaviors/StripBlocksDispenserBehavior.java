package carpetextra.dispenser.behaviors;

import java.util.Collection;
import java.util.Set;

import carpetextra.dispenser.DispenserItemUsageContext;
import carpetextra.mixins.AxeItem_StrippedBlocksAccessorMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class StripBlocksDispenserBehavior extends OptionalDispenseItemBehavior {
    // stripable blocks
    public static final Set<Block> STRIPPED_BLOCKS = AxeItem_StrippedBlocksAccessorMixin.getStrippedBlocks().keySet();
    public static final Set<Block> DEOXIDIZE_BLOCKS = WeatheringCopper.PREVIOUS_BY_BLOCK.get().keySet();
    public static final Set<Block> DEWAX_BLOCKS = HoneycombItem.WAX_OFF_BY_BLOCK.get().keySet();

    // strip results
    public static final Collection<Block> STRIPPED_RESULTS = AxeItem_StrippedBlocksAccessorMixin.getStrippedBlocks().values();
    public static final Set<Block> DEOXIDIZE_RESULTS = WeatheringCopper.PREVIOUS_BY_BLOCK.get().values();
    public static final Set<Block> DEWAX_RESUTLS = HoneycombItem.WAX_OFF_BY_BLOCK.get().values();


    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerLevel world = pointer.level();
        Direction dispenserFacing = pointer.state().getValue(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().relative(dispenserFacing);
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        // check if axe can be used on block
        if(canStrip(frontBlock)) {
            BlockHitResult hitResult = new BlockHitResult(Vec3.atCenterOf(frontBlockPos), dispenserFacing.getOpposite(), frontBlockPos, false);
            UseOnContext context = new DispenserItemUsageContext(world, stack, hitResult);

            // use on block, test if sucessful
            if(stack.getItem().useOn(context).consumesAction()) {
                // damage axe, remove if broken
                stack.hurtAndBreak(1, world, null, (item) -> stack.setCount(0));
                return stack;
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }

    // checks if a block can be stripped with an axe
    public static boolean canStrip(Block block) {
        return STRIPPED_BLOCKS.contains(block) || DEOXIDIZE_BLOCKS.contains(block) || DEWAX_BLOCKS.contains(block);
    }

    // checks if a block is a result of being stripped with an axe
    public static boolean isStripResult(Block block) {
        return STRIPPED_RESULTS.contains(block) || DEOXIDIZE_RESULTS.contains(block) || DEWAX_RESUTLS.contains(block);
    }
}
