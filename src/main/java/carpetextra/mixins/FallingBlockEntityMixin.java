package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity
{
    private int iceCount = 0;
    
    public FallingBlockEntityMixin(EntityType<?> entityType_1, World world_1)
    {
        super(entityType_1, world_1);
    }
    
    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/block/BlockState;)V", at = @At("RETURN"))
    private void onCtor(World world_1, double double_1, double double_2, double double_3, BlockState blockState_1, CallbackInfo ci)
    {
        this.iceCount = 0;
    }
    
    @Inject(
            method = "tick",
            at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 1,
                    target = "Lnet/minecraft/entity/FallingBlockEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true
    )
    private void onTick(CallbackInfo ci, Block block_1, BlockPos blockPos_2, BlockState blockState_1)
    {
        if (block_1.matches(BlockTags.ANVIL) && CarpetExtraSettings.renewablePackedIce && this.world.getBlockState(new BlockPos(this.method_23317(), this.method_23318() - 0.059999999776482582D, this.method_23321())).getBlock() == Blocks.ICE)
        {
            if (iceCount < 2)
            {
                world.breakBlock(blockPos_2.method_10074(), false, null);
                this.onGround = false;
                iceCount++;
                ci.cancel();
            }
            else
            {
                world.setBlockState(blockPos_2.method_10074(), Blocks.PACKED_ICE.getDefaultState(), 3);
                world.playLevelEvent(2001, blockPos_2.method_10074(), Block.getRawIdFromState(Blocks.PACKED_ICE.getDefaultState()));
            }
        }
    }
}
