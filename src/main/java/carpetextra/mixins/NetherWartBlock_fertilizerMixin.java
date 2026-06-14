package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetherWartBlock.class)
public abstract class NetherWartBlock_fertilizerMixin extends VegetationBlock {
    protected NetherWartBlock_fertilizerMixin(Properties settings) {
=======
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetherWartBlock.class)
public abstract class NetherWartBlock_fertilizerMixin extends PlantBlock {
    protected NetherWartBlock_fertilizerMixin(Settings settings) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        super(settings);
    }

    @Override
<<<<<<< HEAD
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int age = state.getValue(NetherWartBlock.AGE);
        if (CarpetExtraSettings.blazeMeal && stack.getItem() == Items.BLAZE_POWDER && age < 3) {
            world.setBlock(pos, this.defaultBlockState().setValue(NetherWartBlock.AGE, age + 1), UPDATE_CLIENTS);
            world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
            if (!player.isCreative()) stack.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
=======
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int age = state.get(NetherWartBlock.AGE);
        if (CarpetExtraSettings.blazeMeal && stack.getItem() == Items.BLAZE_POWDER && age < 3) {
            world.setBlockState(pos, this.getDefaultState().with(NetherWartBlock.AGE, age + 1), NOTIFY_LISTENERS);
            world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
            if (!player.isCreative()) stack.decrement(1);
            return ActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
