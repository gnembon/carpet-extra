package carpetextra.mixins;

import carpet.CarpetServer;
import carpetextra.CarpetExtraSettings;
import carpetextra.fakes.DispenserBlockEntityInterface;
import carpetextra.utils.VoidContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DropperBlock.class)
public class DropperBlock_craftingMixin extends DispenserBlock
{
    protected DropperBlock_craftingMixin(Settings block$Settings_1)
    {
        super(block$Settings_1);
    }

    @Override
    public int getComparatorOutput(BlockState blockState_1, World world_1, BlockPos blockPos_1)
    {
        if (CarpetExtraSettings.autoCraftingDropper)
        {
            BlockPos front = blockPos_1.offset(world_1.getBlockState(blockPos_1).get(DispenserBlock.FACING));
            if (world_1.getBlockState(front).getBlock() == Blocks.CRAFTING_TABLE)
            {
                DispenserBlockEntity dispenserBlockEntity_1 = (DispenserBlockEntity) world_1.getBlockEntity(blockPos_1);
                if (dispenserBlockEntity_1 != null)
                {
                    int filled = 0;
                    for (ItemStack stack : ((DispenserBlockEntityInterface) dispenserBlockEntity_1).getInventory())
                    {
                        if (!stack.isEmpty()) filled++;
                    }
                    return (filled * 15) / 9;
                }
            }
        }
        return super.getComparatorOutput(blockState_1, world_1, blockPos_1);
    }

    private void spawn(World world_1, double double_1, double double_2, double double_3, ItemStack itemStack_1) {
        while(!itemStack_1.isEmpty()) {
            ItemEntity itemEntity_1 = new ItemEntity(world_1, double_1, double_2, double_3, itemStack_1.split(CarpetServer.rand.nextInt(21) + 10));
            itemEntity_1.setVelocity(
                    (CarpetServer.rand.nextDouble()-CarpetServer.rand.nextDouble()) * 0.05,
                    CarpetServer.rand.nextDouble() * 0.05,
                    (CarpetServer.rand.nextDouble()-CarpetServer.rand.nextDouble()) * 0.05
            );
            world_1.spawnEntity(itemEntity_1);
        }

    }

    @Inject(method = "dispense", at = @At("HEAD"), cancellable = true)
    private void tryCraft(ServerWorld world_1, BlockPos blockPos_1, CallbackInfo ci)
    {
        if (!CarpetExtraSettings.autoCraftingDropper) return;
        BlockPos front = blockPos_1.offset(world_1.getBlockState(blockPos_1).get(DispenserBlock.FACING));
        if (world_1.getBlockState(front).getBlock() != Blocks.CRAFTING_TABLE) return;
        DispenserBlockEntity dispenserBlockEntity_1 = (DispenserBlockEntity) world_1.getBlockEntity(blockPos_1);
        if (dispenserBlockEntity_1 == null) return;
        CraftingInventory craftingInventory = new CraftingInventory(new VoidContainer(), 3, 3);
        for (int i=0; i < 9; i++) craftingInventory.setStack(i, dispenserBlockEntity_1.getStack(i));
        CraftingRecipe recipe = world_1.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world_1).orElse(null);
        if (recipe == null) return;
        // crafting it
        Vec3d target = Vec3d.ofBottomCenter(front).add(0.0, 0.2, 0.0);
        ItemStack result = recipe.craft(craftingInventory);
        spawn(world_1, target.x, target.y, target.z, result);

        // copied from CraftingResultSlot.onTakeItem()
        DefaultedList<ItemStack> defaultedList_1 = world_1.getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, craftingInventory, world_1);
        for(int int_1 = 0; int_1 < defaultedList_1.size(); ++int_1) {
            ItemStack itemStack_2 = dispenserBlockEntity_1.getStack(int_1);
            ItemStack itemStack_3 = defaultedList_1.get(int_1);
            if (!itemStack_2.isEmpty()) {
                dispenserBlockEntity_1.removeStack(int_1, 1);
                itemStack_2 = dispenserBlockEntity_1.getStack(int_1);
            }

            if (!itemStack_3.isEmpty()) {
                if (itemStack_2.isEmpty()) {
                    dispenserBlockEntity_1.setStack(int_1, itemStack_3);
                } else if (ItemStack.areItemsEqualIgnoreDamage(itemStack_2, itemStack_3) && ItemStack.areNbtEqual(itemStack_2, itemStack_3)) {
                    itemStack_3.increment(itemStack_2.getCount());
                    dispenserBlockEntity_1.setStack(int_1, itemStack_3);
                } else {
                    spawn(world_1, target.x, target.y, target.z, itemStack_3);
                }
            }
        }
        Vec3d vec = Vec3d.ofCenter(blockPos_1); //+0.5v
        ServerWorld world = (ServerWorld) world_1;
        world.playSound(null, blockPos_1, SoundEvents.ENTITY_VILLAGER_WORK_MASON, SoundCategory.BLOCKS, 0.2f, 2.0f);
        ci.cancel();
    }
}
