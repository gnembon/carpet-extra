package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;
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
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
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
    {
        super(requiredMemoryState);
    }

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
        }
        return villagerData.profession();
    }

    @Inject(method = "validPos", at = @At("HEAD"), cancellable = true)
    private void isValidSoulSand(BlockPos pos, ServerLevel world, CallbackInfoReturnable<Boolean> cir)
    {
        if (isFarmingCleric)
        {
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            Block block2 = world.getBlockState(pos.below()).getBlock();
            cir.setReturnValue(
                    block == Blocks.NETHER_WART && blockState.getValue(NetherWartBlock.AGE)== 3 ||
                            blockState.isAir() && block2 == Blocks.SOUL_SAND);

        }
    }

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
        }
        return state;
    }

    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;",
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
                boolean bl = false;
                if (!itemStack.isEmpty())
                {
                    if (itemStack.getItem() == Items.NETHER_WART)
                    {
                        serverWorld.setBlock(aboveFarmlandPos, Blocks.NETHER_WART.defaultBlockState(), 3);
                        bl = true;
                    }
                }

                if (bl)
                {
                    serverWorld.playSeededSound(null,
                            aboveFarmlandPos.getX(), aboveFarmlandPos.getY(), this.aboveFarmlandPos.getZ(),
                            SoundEvents.NETHER_WART_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F, serverWorld.getRandom().nextLong());
                    itemStack.shrink(1);
                    if (itemStack.isEmpty())
                    {
                        basicInventory.setItem(i, ItemStack.EMPTY);
                    }
                    break;
                }
            }
            return 0;

        }
        return basicInventory.getContainerSize();
    }


}
