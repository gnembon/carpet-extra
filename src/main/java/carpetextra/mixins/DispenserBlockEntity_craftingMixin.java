package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.fakes.DispenserBlockEntityInterface;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DispenserBlockEntity.class)
public abstract class DispenserBlockEntity_craftingMixin extends LootableContainerBlockEntity implements DispenserBlockEntityInterface
{
    @Shadow private DefaultedList<ItemStack> inventory;

    protected DispenserBlockEntity_craftingMixin(BlockEntityType<?> blockEntityType_1)
    {
        super(blockEntityType_1);
    }

    @Override
    public DefaultedList<ItemStack> getInventory()
    {
        return inventory;
    }

    @Override
    public boolean isValidInvStack(int int_1, ItemStack itemStack_1)
    {

        if (CarpetExtraSettings.autoCraftingDropper && world != null)
        {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == Blocks.DROPPER)
            {
                if (world.getBlockState(pos.offset(state.get(DispenserBlock.FACING))).getBlock() == Blocks.CRAFTING_TABLE)
                {
                    return inventory.get(int_1).isEmpty();
                }
            }
        }
        return super.isValidInvStack(int_1, itemStack_1);
    }
}
