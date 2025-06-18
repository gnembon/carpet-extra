package carpetextra.mixins;

import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import carpetextra.helpers.FlowerPotHelper;
import com.llamalad7.mixinextras.sugar.Local;

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

@Mixin(FlowerPotBlock.class)
public abstract class FlowerPotBlockMixin extends Block
{
    @Shadow @Final private Block content;

    public FlowerPotBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(
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
    }
}
