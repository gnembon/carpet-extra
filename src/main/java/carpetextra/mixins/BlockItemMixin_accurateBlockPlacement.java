package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.BlockPlacer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockItem.class)
public class BlockItemMixin_accurateBlockPlacement
{
    @Redirect(method = "getPlacementState", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/Block;getStateForPlacement(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;"
    ))
    private BlockState getAlternatePlacement(Block block, BlockPlaceContext context)
    {
        if (CarpetExtraSettings.accurateBlockPlacement)
        {
            BlockState tryAlternative = BlockPlacer.alternativeBlockPlacement(block, context);
            if (tryAlternative != null)
                return tryAlternative;
        }
        return block.getStateForPlacement(context);
    }

}
