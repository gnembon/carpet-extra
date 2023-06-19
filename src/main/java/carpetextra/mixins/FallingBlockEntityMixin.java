package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
    
    @Inject(method = "tick", cancellable = true, at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/FallingBlockEntity;destroyedOnLanding:Z",
            shift = At.Shift.BEFORE
    ))
    private void onTick(CallbackInfo ci)
    {
        if (getBlockState().isIn(BlockTags.ANVIL))
        {
            if (CarpetExtraSettings.renewableIce)
            {
                Block below = this.getWorld().getBlockState(BlockPos.ofFloored(this.getX(), this.getY() - 0.059999999776482582D, this.getZ())).getBlock();
                if (iceProgression.containsKey(below))
                {
                    if (currentIce != below)
                    {
                        currentIce = below;
                        iceCount = 0;
                    }
                    if (iceCount < 2)
                    {
                        getWorld().breakBlock(getBlockPos().down(), false, null);
                        this.setOnGround(false);
                        iceCount++;
                        ci.cancel();
                    }
                    else
                    {
                        BlockState newBlock = iceProgression.get(below).getDefaultState();
                        getWorld().setBlockState(getBlockPos().down(), newBlock, 3);
                        getWorld().syncWorldEvent(2001, getBlockPos().down(), Block.getRawIdFromState(newBlock));
                    }
                }
            }

            if (CarpetExtraSettings.renewableSand && this.getWorld().getBlockState(BlockPos.ofFloored(this.getX(), this.getY() - 0.06, this.getZ())).getBlock() == Blocks.COBBLESTONE)
            {
                getWorld().breakBlock(getBlockPos().down(), false);
                getWorld().setBlockState(getBlockPos().down(), Blocks.SAND.getDefaultState(), 3);
            }
        }
    }
}
