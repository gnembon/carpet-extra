package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.helpers.DragonEggBedrockBreaking;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DragonEggBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
=======
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin extends Block
{
    @Shadow
<<<<<<< HEAD
    public static boolean isFree(BlockState state)
=======
    public static boolean canFallThrough(BlockState state)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        throw new AssertionError();
    }
    
<<<<<<< HEAD
    @Shadow protected abstract void falling(FallingBlockEntity fallingBlockEntity_1);
    
    public FallingBlockMixin(Properties settings)
=======
    @Shadow protected abstract void configureFallingBlockEntity(FallingBlockEntity fallingBlockEntity_1);
    
    public FallingBlockMixin(Settings settings)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        super(settings);
    }
    
    @SuppressWarnings("ConstantConditions")
<<<<<<< HEAD
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
=======
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
                    if (!world.isClient())
                    {
                        FallingBlockEntity fallingBlock = FallingBlockEntity.spawnFromBlock(world, pos, world.getBlockState(pos) );
                        this.configureFallingBlockEntity(fallingBlock);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
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
                    
<<<<<<< HEAD
                    int minY = CarpetExtraSettings.y0DragonEggBedrockBreaking ? world.getMinY() - 1 : world.getMinY();
                    
                    for (blockPos = pos.below(1); isFree(world.getBlockState(blockPos)) && blockPos.getY() > minY; blockPos = blockPos.below(1))
=======
                    int minY = CarpetExtraSettings.y0DragonEggBedrockBreaking ? world.getBottomY() - 1 : world.getBottomY();
                    
                    for (blockPos = pos.down(1); canFallThrough(world.getBlockState(blockPos)) && blockPos.getY() > minY; blockPos = blockPos.down(1))
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    {
                        ;
                    }
                    
                    if (blockPos.getY() > minY)
                    {
<<<<<<< HEAD
                        world.setBlockAndUpdate(blockPos, this.defaultBlockState());
=======
                        world.setBlockState(blockPos, this.getDefaultState());
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    }
                }
            }
            ci.cancel();
        }
    }
}
