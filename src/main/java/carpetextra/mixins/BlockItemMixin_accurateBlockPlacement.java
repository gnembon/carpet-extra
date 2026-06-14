package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.BlockPlacer;
<<<<<<< HEAD
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
=======
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockItem.class)
public class BlockItemMixin_accurateBlockPlacement
{
    @Redirect(method = "getPlacementState", at = @At(
            value = "INVOKE",
<<<<<<< HEAD
            target = "Lnet/minecraft/world/level/block/Block;getStateForPlacement(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;"
    ))
    private BlockState getAlternatePlacement(Block block, BlockPlaceContext context)
=======
            target = "Lnet/minecraft/block/Block;getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;"
    ))
    private BlockState getAlternatePlacement(Block block, ItemPlacementContext context)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        if (CarpetExtraSettings.accurateBlockPlacement)
        {
            BlockState tryAlternative = BlockPlacer.alternativeBlockPlacement(block, context);
            if (tryAlternative != null)
                return tryAlternative;
        }
<<<<<<< HEAD
        return block.getStateForPlacement(context);
=======
        return block.getPlacementState(context);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }

}
