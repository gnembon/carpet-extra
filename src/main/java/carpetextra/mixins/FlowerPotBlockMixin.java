package carpetextra.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import carpetextra.helpers.FlowerPotHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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

    public FlowerPotBlockMixin(Settings block$Settings_1)
    {
        super(block$Settings_1);
    }

    @Inject(
        method = "onUse",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
            ordinal = 0
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void onActivate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir, ItemStack itemStack, Item i, BlockState blockState) {
        // check if chunk should add force load when flower is placed in pot
        FlowerPotHelper.updateLoadStatus(world, pos, ((FlowerPotBlock) blockState.getBlock()).getContent(), true);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        // check if chunk should remove force load when flower pot is broken
        FlowerPotHelper.updateLoadStatus(world, pos, this.content, false);
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
