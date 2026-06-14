package carpetextra.mixins;

import java.util.Random;
<<<<<<< HEAD
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
import carpetextra.CarpetExtraSettings;
import carpetextra.fakes.DispenserBlockEntityInterface;
import carpetextra.utils.VoidContainer;
=======

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
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DropperBlock.class)
public class DropperBlock_craftingMixin extends DispenserBlock
{
    @Unique private static final Random rand = new Random();
<<<<<<< HEAD
    protected DropperBlock_craftingMixin(Properties settings)
=======
    protected DropperBlock_craftingMixin(Settings settings)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        super(settings);
    }

    @Override
<<<<<<< HEAD
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos, Direction direction)
    {
        if (CarpetExtraSettings.autoCraftingDropper)
        {
            BlockPos front = pos.relative(world.getBlockState(pos).getValue(DispenserBlock.FACING));
=======
    public int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction)
    {
        if (CarpetExtraSettings.autoCraftingDropper)
        {
            BlockPos front = pos.offset(world.getBlockState(pos).get(DispenserBlock.FACING));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
<<<<<<< HEAD
        return super.getAnalogOutputSignal(state, world, pos, direction);
    }

    @Unique private void spawn(Level world, double x, double y, double z, ItemStack stack) {
        while(!stack.isEmpty()) {
            ItemEntity item = new ItemEntity(world, x, y, z, stack.split(rand.nextInt(21) + 10));
            item.setDeltaMovement(
=======
        return super.getComparatorOutput(state, world, pos, direction);
    }

    @Unique private void spawn(World world, double x, double y, double z, ItemStack stack) {
        while(!stack.isEmpty()) {
            ItemEntity item = new ItemEntity(world, x, y, z, stack.split(rand.nextInt(21) + 10));
            item.setVelocity(
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    (rand.nextDouble()-rand.nextDouble()) * 0.05,
                    rand.nextDouble() * 0.05,
                    (rand.nextDouble()-rand.nextDouble()) * 0.05
            );
<<<<<<< HEAD
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
=======
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
        CraftingRecipeInput input = positioned.input();
        int left = positioned.left();
        int top = positioned.top();
        DefaultedList<ItemStack> defaultedList = world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, recipeInput, world).map((r) ->
                                                 r.value().getRecipeRemainders(input)).orElseGet(() -> handleInput(input));

        for (int row = 0; row < recipeInput.getHeight(); ++row) {
            for (int column = 0; column < recipeInput.getWidth(); ++column) {
                int index = column + left + (row + top) * craftingInventory.getWidth();
                ItemStack itemStack = dispenser.getStack(index);
                ItemStack itemStack2 = defaultedList.get(column + row * recipeInput.getWidth());
                if (!itemStack.isEmpty()) {
                    dispenser.removeStack(index, 1);
                    itemStack = dispenser.getStack(index);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                }

                if (!itemStack2.isEmpty()) {
                    if (itemStack.isEmpty()) {
<<<<<<< HEAD
                        dispenser.setItem(index, itemStack2);
                    } else if (ItemStack.isSameItemSameComponents(itemStack, itemStack2)) {
                        itemStack2.grow(itemStack.getCount());
                        dispenser.setItem(index, itemStack2);
=======
                        dispenser.setStack(index, itemStack2);
                    } else if (ItemStack.areItemsAndComponentsEqual(itemStack, itemStack2)) {
                        itemStack2.increment(itemStack.getCount());
                        dispenser.setStack(index, itemStack2);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    } else {
                        spawn(world, target.x, target.y, target.z, itemStack2);
                    }
                }
            }
        }
<<<<<<< HEAD
        world.playSound(null, pos, SoundEvents.VILLAGER_WORK_MASON, SoundSource.BLOCKS, 0.2f, 2.0f);
        ci.cancel();
    }

    @Unique private static NonNullList<ItemStack> handleInput(CraftingInput input)
    {
        NonNullList<ItemStack> list = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < list.size(); ++i)
        {
            list.set(i, input.getItem(i));
=======
        world.playSound(null, pos, SoundEvents.ENTITY_VILLAGER_WORK_MASON, SoundCategory.BLOCKS, 0.2f, 2.0f);
        ci.cancel();
    }

    @Unique private static DefaultedList<ItemStack> handleInput(CraftingRecipeInput input)
    {
        DefaultedList<ItemStack> list = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < list.size(); ++i)
        {
            list.set(i, input.getStackInSlot(i));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }

        return list;
    }
}
