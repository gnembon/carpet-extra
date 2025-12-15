package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
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
    
    public FallingBlockEntityMixin(EntityType<?> type, Level world)
    {
        super(type, world);
    }
    
    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/level/block/state/BlockState;)V", at = @At("RETURN"))
    private void onCtor(Level world, double x, double y, double z, BlockState state, CallbackInfo ci)
    {
        this.iceCount = 0;
    }
    
    @Inject(method = "tick", cancellable = true, at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;cancelDrop:Z",
            shift = At.Shift.BEFORE
    ))
    @SuppressWarnings("resource")
    private void onTick(CallbackInfo ci)
    {
        if (getBlockState().is(BlockTags.ANVIL))
        {
            Level world = this.level();
            if (CarpetExtraSettings.renewableIce)
            {
                Block below = world.getBlockState(BlockPos.containing(this.getX(), this.getY() - 0.059999999776482582D, this.getZ())).getBlock();
                if (iceProgression.containsKey(below))
                {
                    if (currentIce != below)
                    {
                        currentIce = below;
                        iceCount = 0;
                    }
                    if (iceCount < 2)
                    {
                    	world.destroyBlock(blockPosition().below(), false, null);
                        this.setOnGround(false);
                        iceCount++;
                        ci.cancel();
                    }
                    else
                    {
                        BlockState newBlock = iceProgression.get(below).defaultBlockState();
                        world.setBlock(blockPosition().below(), newBlock, 3);
                        world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, blockPosition().below(), Block.getId(newBlock));
                    }
                }
            }

            if (CarpetExtraSettings.renewableSand && world.getBlockState(BlockPos.containing(this.getX(), this.getY() - 0.06, this.getZ())).getBlock() == Blocks.COBBLESTONE)
            {
            	world.destroyBlock(blockPosition().below(), false);
            	world.setBlock(blockPosition().below(), Blocks.SAND.defaultBlockState(), 3);
            }
        }
    }
}
