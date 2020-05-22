package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.FarmerVillagerTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(FarmerVillagerTask.class)
public abstract class FarmerVillagerTask_wartFarmMixin extends Task<VillagerEntity>
{
    @Shadow private boolean ableToPlant;
    @Shadow /*@Nullable*/ private BlockPos currentTarget;
    private boolean isFarmingCleric;

    public FarmerVillagerTask_wartFarmMixin(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState)
    {
        super(requiredMemoryState);
    }

    @Redirect(method = "shouldRun", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/village/VillagerData;getProfession()Lnet/minecraft/village/VillagerProfession;"
    ))
    private VillagerProfession disguiseAsFarmer(VillagerData villagerData)
    {
        isFarmingCleric = false;
        VillagerProfession profession = villagerData.getProfession();
        if (CarpetExtraSettings.clericsFarmWarts && profession == VillagerProfession.CLERIC)
        {
            isFarmingCleric = true;
            return VillagerProfession.FARMER;
        }
        return profession;
    }

    @Inject(method = "isSuitableTarget", at = @At("HEAD"), cancellable = true)
    private void isValidSoulSand(BlockPos blockPos, ServerWorld serverWorld, CallbackInfoReturnable<Boolean> cir)
    {
        if (isFarmingCleric)
        {
            BlockState blockState = serverWorld.getBlockState(blockPos);
            Block block = blockState.getBlock();
            Block block2 = serverWorld.getBlockState(blockPos.down()).getBlock(); // down()
            cir.setReturnValue(
                    block == Blocks.NETHER_WART && blockState.get(NetherWartBlock.AGE)== 3 ||
                            blockState.isAir() && block2 == Blocks.SOUL_SAND && ableToPlant);

        }
    }

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
        }
        return state;
    }

    @Redirect(method = "keepRunning", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;",
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
                boolean bl = false;
                if (!itemStack.isEmpty())
                {
                    if (itemStack.getItem() == Items.NETHER_WART)
                    {
                        serverWorld.setBlockState(currentTarget, Blocks.NETHER_WART.getDefaultState(), 3);
                        bl = true;
                    }
                }

                if (bl)
                {
                    serverWorld.playSound(null,
                            currentTarget.getX(), currentTarget.getY(), this.currentTarget.getZ(),
                            SoundEvents.ITEM_NETHER_WART_PLANT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    itemStack.decrement(1);
                    if (itemStack.isEmpty())
                    {
                        basicInventory.setStack(i, ItemStack.EMPTY);
                    }
                    break;
                }
            }
            return 0;

        }
        return basicInventory.size();
    }


}
