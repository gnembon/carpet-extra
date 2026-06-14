package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
=======
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.FarmerVillagerTask;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.HarvestFarmland;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(HarvestFarmland.class)
public abstract class FarmerVillagerTask_wartFarmMixin extends Behavior<Villager>
{
    //@Shadow private boolean ableToPlant;
    @Shadow /*@Nullable*/ private BlockPos aboveFarmlandPos;
    private boolean isFarmingCleric;

    public FarmerVillagerTask_wartFarmMixin(Map<MemoryModuleType<?>, MemoryStatus> requiredMemoryState)
=======

@Mixin(FarmerVillagerTask.class)
public abstract class FarmerVillagerTask_wartFarmMixin extends MultiTickTask<VillagerEntity>
{
    //@Shadow private boolean ableToPlant;
    @Shadow /*@Nullable*/ private BlockPos currentTarget;
    private boolean isFarmingCleric;

    public FarmerVillagerTask_wartFarmMixin(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        super(requiredMemoryState);
    }

<<<<<<< HEAD
    @Redirect(method = "checkExtraStartConditions", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/npc/VillagerData;profession()Lnet/minecraft/core/Holder;"
    ))
    private Holder<VillagerProfession> disguiseAsFarmer(VillagerData villagerData)
    {
        isFarmingCleric = false;
        Holder<VillagerProfession> profession = villagerData.profession();
        if (CarpetExtraSettings.clericsFarmWarts && profession.is(VillagerProfession.CLERIC))
        {
            isFarmingCleric = true;
            return BuiltInRegistries.VILLAGER_PROFESSION.wrapAsHolder(BuiltInRegistries.VILLAGER_PROFESSION.getValue(VillagerProfession.FARMER));
=======
    @Redirect(method = "shouldRun", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/village/VillagerData;profession()Lnet/minecraft/registry/entry/RegistryEntry;"
    ))
    private RegistryEntry<VillagerProfession> disguiseAsFarmer(VillagerData villagerData)
    {
        isFarmingCleric = false;
        RegistryEntry<VillagerProfession> profession = villagerData.profession();
        if (CarpetExtraSettings.clericsFarmWarts && profession.matchesKey(VillagerProfession.CLERIC))
        {
            isFarmingCleric = true;
            return Registries.VILLAGER_PROFESSION.getEntry(Registries.VILLAGER_PROFESSION.get(VillagerProfession.FARMER));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
        return villagerData.profession();
    }

<<<<<<< HEAD
    @Inject(method = "validPos", at = @At("HEAD"), cancellable = true)
    private void isValidSoulSand(BlockPos pos, ServerLevel world, CallbackInfoReturnable<Boolean> cir)
=======
    @Inject(method = "isSuitableTarget", at = @At("HEAD"), cancellable = true)
    private void isValidSoulSand(BlockPos pos, ServerWorld world, CallbackInfoReturnable<Boolean> cir)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        if (isFarmingCleric)
        {
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
<<<<<<< HEAD
            Block block2 = world.getBlockState(pos.below()).getBlock();
            cir.setReturnValue(
                    block == Blocks.NETHER_WART && blockState.getValue(NetherWartBlock.AGE)== 3 ||
=======
            Block block2 = world.getBlockState(pos.down()).getBlock();
            cir.setReturnValue(
                    block == Blocks.NETHER_WART && blockState.get(NetherWartBlock.AGE)== 3 ||
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                            blockState.isAir() && block2 == Blocks.SOUL_SAND);

        }
    }

<<<<<<< HEAD
    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
            ordinal = 0
    ) )
    private BlockState getWartBlockState(ServerLevel serverWorld, BlockPos pos)
    {
        BlockState state = serverWorld.getBlockState(pos);
        if (isFarmingCleric && state.getBlock() == Blocks.NETHER_WART && state.getValue(NetherWartBlock.AGE) == 3)
        {
            return ((CropBlock)Blocks.WHEAT).getStateForAge(((CropBlock)Blocks.WHEAT).getMaxAge());
=======
    @Redirect(method = "keepRunning", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
            ordinal = 0
    ) )
    private BlockState getWartBlockState(ServerWorld serverWorld, BlockPos pos)
    {
        BlockState state = serverWorld.getBlockState(pos);
        if (isFarmingCleric && state.getBlock() == Blocks.NETHER_WART && state.get(NetherWartBlock.AGE) == 3)
        {
            return ((CropBlock)Blocks.WHEAT).withAge(((CropBlock)Blocks.WHEAT).getMaxAge());
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
        return state;
    }

<<<<<<< HEAD
    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;",
=======
    @Redirect(method = "keepRunning", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;",
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            ordinal = 1
    ))
    private Block getFarmlangBLock(BlockState blockState)
    {
        Block block = blockState.getBlock();
        if (isFarmingCleric && block == Blocks.SOUL_SAND)
        {
            return Blocks.FARMLAND;
        }
        return block;
    }

<<<<<<< HEAD
    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/SimpleContainer;size()I"
    ))
    private int plantWart(SimpleContainer basicInventory, ServerLevel serverWorld, Villager villagerEntity, long l)
    {
        if (isFarmingCleric) // fill cancel that for loop by setting length to 0
        {
            for(int i = 0; i < basicInventory.getContainerSize(); ++i)
            {
                ItemStack itemStack = basicInventory.getItem(i);
=======
    @Redirect(method = "keepRunning", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/SimpleInventory;size()I"
    ))
    private int plantWart(SimpleInventory basicInventory, ServerWorld serverWorld, VillagerEntity villagerEntity, long l)
    {
        if (isFarmingCleric) // fill cancel that for loop by setting length to 0
        {
            for(int i = 0; i < basicInventory.size(); ++i)
            {
                ItemStack itemStack = basicInventory.getStack(i);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                boolean bl = false;
                if (!itemStack.isEmpty())
                {
                    if (itemStack.getItem() == Items.NETHER_WART)
                    {
<<<<<<< HEAD
                        serverWorld.setBlock(aboveFarmlandPos, Blocks.NETHER_WART.defaultBlockState(), 3);
=======
                        serverWorld.setBlockState(currentTarget, Blocks.NETHER_WART.getDefaultState(), 3);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                        bl = true;
                    }
                }

                if (bl)
                {
<<<<<<< HEAD
                    serverWorld.playSeededSound(null,
                            aboveFarmlandPos.getX(), aboveFarmlandPos.getY(), this.aboveFarmlandPos.getZ(),
                            SoundEvents.NETHER_WART_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F, serverWorld.getRandom().nextLong());
                    itemStack.shrink(1);
                    if (itemStack.isEmpty())
                    {
                        basicInventory.setItem(i, ItemStack.EMPTY);
=======
                    serverWorld.playSound(null,
                            currentTarget.getX(), currentTarget.getY(), this.currentTarget.getZ(),
                            SoundEvents.ITEM_NETHER_WART_PLANT, SoundCategory.BLOCKS, 1.0F, 1.0F, serverWorld.getRandom().nextLong());
                    itemStack.decrement(1);
                    if (itemStack.isEmpty())
                    {
                        basicInventory.setStack(i, ItemStack.EMPTY);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    }
                    break;
                }
            }
            return 0;

        }
<<<<<<< HEAD
        return basicInventory.getContainerSize();
=======
        return basicInventory.size();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }


}
