package carpetextra.mixins;

import java.util.Random;

import carpetextra.CarpetExtraSettings;
import carpetextra.fakes.DispenserBlockEntityInterface;
import carpetextra.utils.VoidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.DropperBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DropperBlock.class)
public class DropperBlock_craftingMixin extends DispenserBlock
{
    @Unique private static final Random rand = new Random();
    protected DropperBlock_craftingMixin(Properties settings)
    {
        super(settings);
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos, Direction direction)
    {
        if (CarpetExtraSettings.autoCraftingDropper)
        {
            BlockPos front = pos.relative(world.getBlockState(pos).getValue(DispenserBlock.FACING));
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
        return super.getAnalogOutputSignal(state, world, pos, direction);
    }

    @Unique private void spawn(Level world, double x, double y, double z, ItemStack stack) {
        while(!stack.isEmpty()) {
            ItemEntity item = new ItemEntity(world, x, y, z, stack.split(rand.nextInt(21) + 10));
            item.setDeltaMovement(
                    (rand.nextDouble()-rand.nextDouble()) * 0.05,
                    rand.nextDouble() * 0.05,
                    (rand.nextDouble()-rand.nextDouble()) * 0.05
            );
            world.addFreshEntity(item);
        }
    }

    @Inject(method = "dispenseFrom", at = @At("HEAD"), cancellable = true)
    private void tryCraft(ServerLevel world, BlockState state, BlockPos pos, CallbackInfo ci)
    {
        if (!CarpetExtraSettings.autoCraftingDropper) return;
        BlockPos front = pos.relative(world.getBlockState(pos).getValue(DispenserBlock.FACING));
        if (world.getBlockState(front).getBlock() != Blocks.CRAFTING_TABLE) return;
        DispenserBlockEntity dispenser = (DispenserBlockEntity) world.getBlockEntity(pos);
        if (dispenser == null) return;
        TransientCraftingContainer craftingInventory = new TransientCraftingContainer(new VoidContainer(), 3, 3);
        for (int i=0; i < 9; i++) craftingInventory.setItem(i, dispenser.getItem(i));
        CraftingInput recipeInput = craftingInventory.asCraftInput();
        RecipeHolder<CraftingRecipe> recipe = world.recipeAccess().getRecipeFor(RecipeType.CRAFTING, recipeInput, world).orElse(null);
        if (recipe == null) return;
        // crafting it
        Vec3 target = Vec3.atBottomCenterOf(front).add(0.0, 0.2, 0.0);
        ItemStack result = recipe.value().assemble(recipeInput, world.registryAccess());
        spawn(world, target.x, target.y, target.z, result);

        // copied from CraftingResultSlot.onTakeItem()
        CraftingInput.Positioned positioned = craftingInventory.asPositionedCraftInput();
        CraftingInput input = positioned.input();
        int left = positioned.left();
        int top = positioned.top();
        NonNullList<ItemStack> defaultedList = world.recipeAccess().getRecipeFor(RecipeType.CRAFTING, recipeInput, world).map((r) ->
                                                 r.value().getRemainingItems(input)).orElseGet(() -> handleInput(input));

        for (int row = 0; row < recipeInput.height(); ++row) {
            for (int column = 0; column < recipeInput.width(); ++column) {
                int index = column + left + (row + top) * craftingInventory.getWidth();
                ItemStack itemStack = dispenser.getItem(index);
                ItemStack itemStack2 = defaultedList.get(column + row * recipeInput.width());
                if (!itemStack.isEmpty()) {
                    dispenser.removeItem(index, 1);
                    itemStack = dispenser.getItem(index);
                }

                if (!itemStack2.isEmpty()) {
                    if (itemStack.isEmpty()) {
                        dispenser.setItem(index, itemStack2);
                    } else if (ItemStack.isSameItemSameComponents(itemStack, itemStack2)) {
                        itemStack2.grow(itemStack.getCount());
                        dispenser.setItem(index, itemStack2);
                    } else {
                        spawn(world, target.x, target.y, target.z, itemStack2);
                    }
                }
            }
        }
        world.playSound(null, pos, SoundEvents.VILLAGER_WORK_MASON, SoundSource.BLOCKS, 0.2f, 2.0f);
        ci.cancel();
    }

    @Unique private static NonNullList<ItemStack> handleInput(CraftingInput input)
    {
        NonNullList<ItemStack> list = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < list.size(); ++i)
        {
            list.set(i, input.getItem(i));
        }

        return list;
    }
}
