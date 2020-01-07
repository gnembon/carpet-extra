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
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin extends Block
{
    @Shadow
    public static boolean canFallThrough(BlockState blockState_1)
    {
        return false;
    }
    
    @Shadow protected abstract void configureFallingBlockEntity(FallingBlockEntity fallingBlockEntity_1);
    
    public FallingBlockMixin(Settings block$Settings_1)
    {
        super(block$Settings_1);
    }
    
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    private void onTryStartFalling(BlockState blockState_1, ServerWorld serverWorld_1, BlockPos blockPos_1, Random random_1, CallbackInfo ci)
    {
        if (CarpetExtraSettings.dragonEggBedrockBreaking && (FallingBlock)(Object)this instanceof DragonEggBlock)
        {
            if (canFallThrough(serverWorld_1.getBlockState(blockPos_1.down(1))) && blockPos_1.getY() >= 0)
            {
                if (!DragonEggBedrockBreaking.fallInstantly &&
                        serverWorld_1.getChunkManager().shouldTickChunk(new ChunkPos(blockPos_1)))
                {
                    if (!serverWorld_1.isClient)
                    {
                        FallingBlockEntity fallingBlockEntity_1 = new FallingBlockEntity(serverWorld_1, (double) blockPos_1.getX() + 0.5D, (double) blockPos_1.getY(), (double) blockPos_1.getZ() + 0.5D, serverWorld_1.getBlockState(blockPos_1));
                        this.configureFallingBlockEntity(fallingBlockEntity_1);
                        serverWorld_1.spawnEntity(fallingBlockEntity_1);
                    }
                }
                else
                {
                    if (serverWorld_1.getBlockState(blockPos_1).getBlock() == this)
                    {
                        serverWorld_1.removeBlock(blockPos_1, false);
                    }

                    BlockPos blockPos;
                    
                    int minY = CarpetExtraSettings.y0DragonEggBedrockBreaking ? -1 : 0;
                    
                    for (blockPos = blockPos_1.down(1); canFallThrough(serverWorld_1.getBlockState(blockPos)) && blockPos.getY() > minY; blockPos = blockPos.down(1))
                    {
                        ;
                    }
                    
                    if (blockPos.getY() > minY)
                    {
                        serverWorld_1.setBlockState(blockPos, this.getDefaultState());
                    }
                }
            }
            ci.cancel();
        }
    }
}
