package carpetextra.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import carpetextra.helpers.FlowerPotHelper;
import com.llamalad7.mixinextras.sugar.Local;
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

@Mixin(FlowerPotBlock.class)
public abstract class FlowerPotBlockMixin extends Block
{
    @Shadow @Final private Block potted;

    public FlowerPotBlockMixin(Properties settings)
    {
        super(settings);
    }

    @Inject(
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
    }
}
