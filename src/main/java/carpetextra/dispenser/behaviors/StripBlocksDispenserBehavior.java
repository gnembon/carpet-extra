package carpetextra.dispenser.behaviors;

import java.util.Collection;
import java.util.Set;
<<<<<<< HEAD
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
import carpetextra.dispenser.DispenserItemUsageContext;
import carpetextra.mixins.AxeItem_StrippedBlocksAccessorMixin;

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
=======

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
        ServerWorld world = pointer.world();
        Direction dispenserFacing = pointer.state().get(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().offset(dispenserFacing);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        BlockState frontBlockState = world.getBlockState(frontBlockPos);
        Block frontBlock = frontBlockState.getBlock();

        // check if axe can be used on block
        if(canStrip(frontBlock)) {
<<<<<<< HEAD
            BlockHitResult hitResult = new BlockHitResult(Vec3.atCenterOf(frontBlockPos), dispenserFacing.getOpposite(), frontBlockPos, false);
            UseOnContext context = new DispenserItemUsageContext(world, stack, hitResult);

            // use on block, test if sucessful
            if(stack.getItem().useOn(context).consumesAction()) {
                // damage axe, remove if broken
                stack.hurtAndBreak(1, world, null, (item) -> stack.setCount(0));
=======
            BlockHitResult hitResult = new BlockHitResult(Vec3d.ofCenter(frontBlockPos), dispenserFacing.getOpposite(), frontBlockPos, false);
            ItemUsageContext context = new DispenserItemUsageContext(world, stack, hitResult);

            // use on block, test if sucessful
            if(stack.getItem().useOnBlock(context).isAccepted()) {
                // damage axe, remove if broken
                stack.damage(1, world, null, (item) -> stack.setCount(0));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
