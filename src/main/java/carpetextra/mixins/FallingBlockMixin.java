package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.helpers.DragonEggBedrockBreaking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin extends Block
{
    @Shadow
    public static boolean canFallThrough(BlockState state)
    {
        throw new AssertionError();
    }
    
    @Shadow protected abstract void configureFallingBlockEntity(FallingBlockEntity fallingBlockEntity_1);
    
    public FallingBlockMixin(Settings settings)
    {
        super(settings);
    }
    
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    private void onTryStartFalling(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci)
    {
        if (CarpetExtraSettings.dragonEggBedrockBreaking && (FallingBlock)(Object)this instanceof DragonEggBlock)
        {
            if (canFallThrough(world.getBlockState(pos.down(1))) && pos.getY() >= world.getBottomY())
            {
                if (!DragonEggBedrockBreaking.fallInstantly &&
                        world.shouldTickEntityAt(pos))
                {
                    if (!world.isClient)
                    {
                        FallingBlockEntity fallingBlock = FallingBlockEntity.spawnFromBlock(world, pos, world.getBlockState(pos) );
                        this.configureFallingBlockEntity(fallingBlock);
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
                    
                    int minY = CarpetExtraSettings.y0DragonEggBedrockBreaking ? world.getBottomY() - 1 : world.getBottomY();
                    
                    for (blockPos = pos.down(1); canFallThrough(world.getBlockState(blockPos)) && blockPos.getY() > minY; blockPos = blockPos.down(1))
                    {
                        ;
                    }
                    
                    if (blockPos.getY() > minY)
                    {
                        world.setBlockState(blockPos, this.getDefaultState());
                    }
                }
            }
            ci.cancel();
        }
    }
}
