package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.fakes.DispenserBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DispenserBlockEntity.class)
public abstract class DispenserBlockEntity_craftingMixin extends RandomizableContainerBlockEntity implements DispenserBlockEntityInterface
{
    @Shadow private NonNullList<ItemStack> items;

    protected DispenserBlockEntity_craftingMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }


    @Override
    public NonNullList<ItemStack> getInventory()
    {
        return items;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack)
    {

        if (CarpetExtraSettings.autoCraftingDropper && level != null)
        {
            BlockState state = level.getBlockState(worldPosition);
            if (state.getBlock() == Blocks.DROPPER)
            {
                if (level.getBlockState(worldPosition.relative(state.getValue(DispenserBlock.FACING))).getBlock() == Blocks.CRAFTING_TABLE)
                {
                    return items.get(slot).isEmpty();
                }
            }
        }
        return super.canPlaceItem(slot, stack);
    }
}
