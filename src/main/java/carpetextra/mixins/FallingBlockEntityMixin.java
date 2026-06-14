package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
=======
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

>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
<<<<<<< HEAD
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
=======
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

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
    
<<<<<<< HEAD
    public FallingBlockEntityMixin(EntityType<?> type, Level world)
=======
    public FallingBlockEntityMixin(EntityType<?> type, World world)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        super(type, world);
    }
    
<<<<<<< HEAD
    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/level/block/state/BlockState;)V", at = @At("RETURN"))
    private void onCtor(Level world, double x, double y, double z, BlockState state, CallbackInfo ci)
=======
    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/block/BlockState;)V", at = @At("RETURN"))
    private void onCtor(World world, double x, double y, double z, BlockState state, CallbackInfo ci)
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        this.iceCount = 0;
    }
    
    @Inject(method = "tick", cancellable = true, at = @At(
            value = "FIELD",
<<<<<<< HEAD
            target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;cancelDrop:Z",
=======
            target = "Lnet/minecraft/entity/FallingBlockEntity;destroyedOnLanding:Z",
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            shift = At.Shift.BEFORE
    ))
    private void onTick(CallbackInfo ci)
    {
<<<<<<< HEAD
        if (getBlockState().is(BlockTags.ANVIL))
        {
            Level world = this.level();
            if (CarpetExtraSettings.renewableIce)
            {
                Block below = world.getBlockState(BlockPos.containing(this.getX(), this.getY() - 0.059999999776482582D, this.getZ())).getBlock();
=======
        if (getBlockState().isIn(BlockTags.ANVIL))
        {
            World world = this.getEntityWorld();
            if (CarpetExtraSettings.renewableIce)
            {
                Block below = world.getBlockState(BlockPos.ofFloored(this.getX(), this.getY() - 0.059999999776482582D, this.getZ())).getBlock();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                if (iceProgression.containsKey(below))
                {
                    if (currentIce != below)
                    {
                        currentIce = below;
                        iceCount = 0;
                    }
                    if (iceCount < 2)
                    {
<<<<<<< HEAD
                    	world.destroyBlock(blockPosition().below(), false, null);
=======
                    	world.breakBlock(getBlockPos().down(), false, null);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                        this.setOnGround(false);
                        iceCount++;
                        ci.cancel();
                    }
                    else
                    {
<<<<<<< HEAD
                        BlockState newBlock = iceProgression.get(below).defaultBlockState();
                        world.setBlock(blockPosition().below(), newBlock, 3);
                        world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, blockPosition().below(), Block.getId(newBlock));
=======
                        BlockState newBlock = iceProgression.get(below).getDefaultState();
                        world.setBlockState(getBlockPos().down(), newBlock, 3);
                        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, getBlockPos().down(), Block.getRawIdFromState(newBlock));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                    }
                }
            }

<<<<<<< HEAD
            if (CarpetExtraSettings.renewableSand && world.getBlockState(BlockPos.containing(this.getX(), this.getY() - 0.06, this.getZ())).getBlock() == Blocks.COBBLESTONE)
            {
            	world.destroyBlock(blockPosition().below(), false);
            	world.setBlock(blockPosition().below(), Blocks.SAND.defaultBlockState(), 3);
=======
            if (CarpetExtraSettings.renewableSand && world.getBlockState(BlockPos.ofFloored(this.getX(), this.getY() - 0.06, this.getZ())).getBlock() == Blocks.COBBLESTONE)
            {
            	world.breakBlock(getBlockPos().down(), false);
            	world.setBlockState(getBlockPos().down(), Blocks.SAND.getDefaultState(), 3);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            }
        }
    }
}
