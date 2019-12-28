package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.helpers.DragonEggBedrockBreaking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    @Inject(method = "tryStartFalling", at = @At("HEAD"), cancellable = true)
    private void onTryStartFalling(World world, BlockPos pos, CallbackInfo ci)
    {
        if (CarpetExtraSettings.dragonEggBedrockBreaking && (FallingBlock)(Object)this instanceof DragonEggBlock)
        {
            if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0)
            {
                if (!DragonEggBedrockBreaking.fallInstantly && world.isAreaLoaded(pos.add(-32, -32, -32),  pos.add(32, 32, 32)))
                {
                    if (!world.isClient)
                    {
                        FallingBlockEntity fallingBlockEntity_1 = new FallingBlockEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
                        this.configureFallingBlockEntity(fallingBlockEntity_1);
                        world.spawnEntity(fallingBlockEntity_1);
                    }
                }
                else
                {
                    if (world.getBlockState(pos).getBlock() == this)
                    {
                        world.clearBlockState(pos, false);
                    }
                    
                    BlockPos blockPos;
    
                    int minY = CarpetExtraSettings.y0DragonEggBedrockBreaking ? -1 : 0;
                    
                    for (blockPos = pos.down(); canFallThrough(world.getBlockState(blockPos)) && blockPos.getY() > minY; blockPos = blockPos.down())
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
