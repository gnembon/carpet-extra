package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.fakes.DispenserBlockEntityInterface;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
=======
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DispenserBlockEntity.class)
<<<<<<< HEAD
public abstract class DispenserBlockEntity_craftingMixin extends RandomizableContainerBlockEntity implements DispenserBlockEntityInterface
{
    @Shadow private NonNullList<ItemStack> items;
=======
public abstract class DispenserBlockEntity_craftingMixin extends LootableContainerBlockEntity implements DispenserBlockEntityInterface
{
    @Shadow private DefaultedList<ItemStack> inventory;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

    protected DispenserBlockEntity_craftingMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }


    @Override
<<<<<<< HEAD
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
=======
    public DefaultedList<ItemStack> getInventory()
    {
        return inventory;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack)
    {

        if (CarpetExtraSettings.autoCraftingDropper && world != null)
        {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == Blocks.DROPPER)
            {
                if (world.getBlockState(pos.offset(state.get(DispenserBlock.FACING))).getBlock() == Blocks.CRAFTING_TABLE)
                {
                    return inventory.get(slot).isEmpty();
                }
            }
        }
        return super.isValid(slot, stack);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
