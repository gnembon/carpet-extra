package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.helpers.DragonEggBedrockBreaking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DragonEggBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin extends Block
{
    @Shadow
    public static boolean isFree(BlockState state)
    {
        throw new AssertionError();
    }
    
    @Shadow protected abstract void falling(FallingBlockEntity fallingBlockEntity_1);
    
    public FallingBlockMixin(Properties settings)
    {
        super(settings);
    }
    
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTryStartFalling(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci)
    {
        if (CarpetExtraSettings.dragonEggBedrockBreaking && (FallingBlock)(Object)this instanceof DragonEggBlock)
        {
            if (isFree(world.getBlockState(pos.below(1))) && pos.getY() >= world.getMinY())
            {
                if (!DragonEggBedrockBreaking.fallInstantly &&
                        world.isPositionEntityTicking(pos))
                {
                    if (!world.isClientSide())
                    {
                        FallingBlockEntity fallingBlock = FallingBlockEntity.fall(world, pos, world.getBlockState(pos) );
                        this.falling(fallingBlock);
                        //serverWorld_1.spawnEntity(fallingBlock);
                    }
                }
                else
                {
                    if (world.getBlockState(pos).getBlock() == this)
                    {
                        world.removeBlock(pos, false);
                    }

                    BlockPos blockPos;
                    
                    int minY = CarpetExtraSettings.y0DragonEggBedrockBreaking ? world.getMinY() - 1 : world.getMinY();
                    
                    for (blockPos = pos.below(1); isFree(world.getBlockState(blockPos)) && blockPos.getY() > minY; blockPos = blockPos.below(1))
                    {
                        ;
                    }
                    
                    if (blockPos.getY() > minY)
                    {
                        world.setBlockAndUpdate(blockPos, this.defaultBlockState());
                    }
                }
            }
            ci.cancel();
        }
    }
}
