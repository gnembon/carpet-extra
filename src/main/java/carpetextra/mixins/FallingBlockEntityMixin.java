package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity
{
    @Shadow public abstract BlockState getBlockState();

    private int iceCount = 0;
    private Block currentIce = null;
    private static final Map<Block, Block> iceProgression = Map.of(
            Blocks.FROSTED_ICE, Blocks.ICE,
            Blocks.ICE, Blocks.PACKED_ICE,
            Blocks.PACKED_ICE, Blocks.BLUE_ICE
    );
    
    public FallingBlockEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }
    
    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/block/BlockState;)V", at = @At("RETURN"))
    private void onCtor(World world, double x, double y, double z, BlockState state, CallbackInfo ci)
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
            World world = this.getWorld();
            if (CarpetExtraSettings.renewableIce)
            {
                Block below = world.getBlockState(BlockPos.ofFloored(this.getX(), this.getY() - 0.059999999776482582D, this.getZ())).getBlock();
                if (iceProgression.containsKey(below))
                {
                    if (currentIce != below)
                    {
                        currentIce = below;
                        iceCount = 0;
                    }
                    if (iceCount < 2)
                    {
                    	world.breakBlock(getBlockPos().down(), false, null);
                        this.setOnGround(false);
                        iceCount++;
                        ci.cancel();
                    }
                    else
                    {
                        BlockState newBlock = iceProgression.get(below).getDefaultState();
                        world.setBlockState(getBlockPos().down(), newBlock, 3);
                        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, getBlockPos().down(), Block.getRawIdFromState(newBlock));
                    }
                }
            }

            if (CarpetExtraSettings.renewableSand && world.getBlockState(BlockPos.ofFloored(this.getX(), this.getY() - 0.06, this.getZ())).getBlock() == Blocks.COBBLESTONE)
            {
            	world.breakBlock(getBlockPos().down(), false);
            	world.setBlockState(getBlockPos().down(), Blocks.SAND.getDefaultState(), 3);
            }
        }
    }
}
