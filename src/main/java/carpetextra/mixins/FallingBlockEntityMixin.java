package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity
{
    @Shadow public abstract BlockState getBlockState();

    private int iceCount = 0;
    private Block currentIce = null;
    private Map<Block, Block> iceProgression = ImmutableMap.of(
            Blocks.FROSTED_ICE, Blocks.ICE,
            Blocks.ICE, Blocks.PACKED_ICE,
            Blocks.PACKED_ICE, Blocks.BLUE_ICE
    );
    
    public FallingBlockEntityMixin(EntityType<?> entityType_1, World world_1)
    {
        super(entityType_1, world_1);
    }
    
    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/block/BlockState;)V", at = @At("RETURN"))
    private void onCtor(World world_1, double double_1, double double_2, double double_3, BlockState blockState_1, CallbackInfo ci)
    {
        this.iceCount = 0;
    }
    
    @Inject(method = "tick", at = @At(value = "INVOKE", ordinal = 0,
            target = "Lnet/minecraft/entity/FallingBlockEntity;remove()V"),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 1)),
            locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true
    )
    private void onTick(CallbackInfo ci, Block block_1, BlockPos blockPos_2, boolean b1, boolean bl2, BlockState blockState_1)
    {
        if (getBlockState().isIn(BlockTags.ANVIL))
        {
            if (CarpetExtraSettings.renewableIce)
            {
                Block below = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 0.059999999776482582D, this.getZ())).getBlock();
                if (iceProgression.containsKey(below))
                {
                    if (currentIce != below)
                    {
                        currentIce = below;
                        iceCount = 0;
                    }
                    if (iceCount < 2)
                    {
                        world.breakBlock(blockPos_2.down(), false, null);
                        this.onGround = false;
                        iceCount++;
                        ci.cancel();
                    }
                    else
                    {
                        BlockState newBlock = iceProgression.get(below).getDefaultState();
                        world.setBlockState(blockPos_2.down(), newBlock, 3);
                        world.syncWorldEvent(2001, blockPos_2.down(), Block.getRawIdFromState(newBlock));
                    }
                }
            }

            if (CarpetExtraSettings.renewableSand && this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 0.06, this.getZ())).getBlock() == Blocks.COBBLESTONE)
            {
                world.breakBlock(blockPos_2.down(), false);
                world.setBlockState(blockPos_2.down(), Blocks.SAND.getDefaultState(), 3);
            }
        }
    }
}
