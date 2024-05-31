package carpetextra.mixins;

import java.util.Random;

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
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
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
    private static final Random rand = new Random();
    protected DropperBlock_craftingMixin(Settings settings)
    {
        super(settings);
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos)
    {
        if (CarpetExtraSettings.autoCraftingDropper)
        {
            BlockPos front = pos.offset(world.getBlockState(pos).get(DispenserBlock.FACING));
            if (world.getBlockState(front).getBlock() == Blocks.CRAFTING_TABLE)
            {
                DispenserBlockEntity dispenser = (DispenserBlockEntity) world.getBlockEntity(pos);
                if (dispenser != null)
                {
                    int filled = 0;
                    for (ItemStack stack : ((DispenserBlockEntityInterface) dispenser).getInventory())
                    {
                        if (!stack.isEmpty()) filled++;
                    }
                    return (filled * 15) / 9;
                }
            }
        }
        return super.getComparatorOutput(state, world, pos);
    }

    private void spawn(World world, double x, double y, double z, ItemStack stack) {
        while(!stack.isEmpty()) {
            ItemEntity item = new ItemEntity(world, x, y, z, stack.split(rand.nextInt(21) + 10));
            item.setVelocity(
                    (rand.nextDouble()-rand.nextDouble()) * 0.05,
                    rand.nextDouble() * 0.05,
                    (rand.nextDouble()-rand.nextDouble()) * 0.05
            );
            world.spawnEntity(item);
        }
    }

    @Inject(method = "dispense", at = @At("HEAD"), cancellable = true)
    private void tryCraft(ServerWorld world, BlockState state, BlockPos pos, CallbackInfo ci)
    {
        if (!CarpetExtraSettings.autoCraftingDropper) return;
        BlockPos front = pos.offset(world.getBlockState(pos).get(DispenserBlock.FACING));
        if (world.getBlockState(front).getBlock() != Blocks.CRAFTING_TABLE) return;
        DispenserBlockEntity dispenser = (DispenserBlockEntity) world.getBlockEntity(pos);
        if (dispenser == null) return;
        CraftingInventory craftingInventory = new CraftingInventory(new VoidContainer(), 3, 3);
        for (int i=0; i < 9; i++) craftingInventory.setStack(i, dispenser.getStack(i));
        CraftingRecipeInput recipeInput = craftingInventory.createRecipeInput();
        RecipeEntry<CraftingRecipe> recipe = world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, recipeInput, world).orElse(null);
        if (recipe == null) return;
        // crafting it
        Vec3d target = Vec3d.ofBottomCenter(front).add(0.0, 0.2, 0.0);
        ItemStack result = recipe.value().craft(recipeInput, world.getRegistryManager());
        spawn(world, target.x, target.y, target.z, result);

        // copied from CraftingResultSlot.onTakeItem()
        CraftingRecipeInput.Positioned positioned = craftingInventory.createPositionedRecipeInput();
        int left = positioned.left();
        int top = positioned.top();
        DefaultedList<ItemStack> defaultedList = world.getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, recipeInput, world);

        for (int row = 0; row < recipeInput.getHeight(); ++row) {
            for (int column = 0; column < recipeInput.getWidth(); ++column) {
                int index = column + left + (row + top) * craftingInventory.getWidth();
                ItemStack itemStack = dispenser.getStack(index);
                ItemStack itemStack2 = defaultedList.get(column + row * craftingInventory.getWidth());
                if (!itemStack.isEmpty()) {
                    dispenser.removeStack(index, 1);
                    itemStack = dispenser.getStack(index);
                }

                if (!itemStack2.isEmpty()) {
                    if (itemStack.isEmpty()) {
                        dispenser.setStack(index, itemStack2);
                    } else if (ItemStack.areItemsAndComponentsEqual(itemStack, itemStack2)) {
                        itemStack2.increment(itemStack.getCount());
                        dispenser.setStack(index, itemStack2);
                    } else {
                        spawn(world, target.x, target.y, target.z, itemStack2);
                    }
                }
            }
        }
        world.playSound(null, pos, SoundEvents.ENTITY_VILLAGER_WORK_MASON, SoundCategory.BLOCKS, 0.2f, 2.0f);
        ci.cancel();
    }
}
