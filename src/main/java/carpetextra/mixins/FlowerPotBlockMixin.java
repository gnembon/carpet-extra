package carpetextra.mixins;

<<<<<<< HEAD
=======
import net.minecraft.server.world.ServerWorld;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import carpetextra.helpers.FlowerPotHelper;
import com.llamalad7.mixinextras.sugar.Local;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
=======

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

@Mixin(FlowerPotBlock.class)
public abstract class FlowerPotBlockMixin extends Block
{
<<<<<<< HEAD
    @Shadow @Final private Block potted;

    public FlowerPotBlockMixin(Properties settings)
=======
    @Shadow @Final private Block content;

    public FlowerPotBlockMixin(Settings settings)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        super(settings);
    }

    @Inject(
<<<<<<< HEAD
            method = "useItemOn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
                    ordinal = 0
            )
    )
    private void onActivate(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir, @Local(ordinal = 1) BlockState blockState) {
        // check if chunk should add force load when flower is placed in pot
        FlowerPotHelper.updateLoadStatus(world, pos, ((FlowerPotBlock) blockState.getBlock()).getPotted(), true);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
        // check if chunk should remove force load when flower pot is broken
        FlowerPotHelper.updateLoadStatus(world, pos, this.potted, false);
        super.affectNeighborsAfterRemoval(state, world, pos, moved);
=======
            method = "onUseWithItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    ordinal = 0
            )
    )
    private void onActivate(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir, @Local(ordinal = 1) BlockState blockState) {
        // check if chunk should add force load when flower is placed in pot
        FlowerPotHelper.updateLoadStatus(world, pos, ((FlowerPotBlock) blockState.getBlock()).getContent(), true);
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        // check if chunk should remove force load when flower pot is broken
        FlowerPotHelper.updateLoadStatus(world, pos, this.content, false);
        super.onStateReplaced(state, world, pos, moved);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
