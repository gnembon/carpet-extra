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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntity_crushIceMixin extends Entity {
    @Shadow public abstract BlockState getBlockState();

    private static final Map<Block, Block> ICE_PROGRESSION = ImmutableMap.of(
        Blocks.FROSTED_ICE, Blocks.ICE,
        Blocks.ICE, Blocks.PACKED_ICE,
        Blocks.PACKED_ICE, Blocks.BLUE_ICE
    );

    @Unique
    private int crushedIceCount = 0;
    @Unique
    private Block currentCrushingIce = null;

    public FallingBlockEntity_crushIceMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
        method = "tick",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/FallingBlockEntity;destroyedOnLanding:Z",
            shift = At.Shift.BEFORE
        ),
        cancellable = true
    )
    private void crushIce(CallbackInfo ci) {
        // check if rule is enabled and falling block is anvil
        if(CarpetExtraSettings.renewableIce && this.getBlockState().isIn(BlockTags.ANVIL)) {
            // get block below
            BlockPos belowPos = new BlockPos(this.getX(), this.getY() - 0.06, this.getZ());
            Block belowBlock = this.world.getBlockState(belowPos).getBlock();

            // check if block below is a convertable ice type
            if(ICE_PROGRESSION.containsKey(belowBlock)) {
                // reset ice type and count if necessary
                if(this.currentCrushingIce != belowBlock) {
                    this.currentCrushingIce = belowBlock;
                    this.crushedIceCount = 0;
                }

                // play breaking sound/animation
                this.world.breakBlock(belowPos, false);

                // increment crushed ice count and check if crushed amount is below required count
                if(++this.crushedIceCount < CarpetExtraSettings.renewableIceCrushCount) {
                    this.onGround = false;
                    ci.cancel();
                }
                else {
                    // set block to next ice type
                    Block nextBlock = ICE_PROGRESSION.get(belowBlock);
                    this.world.setBlockState(belowPos, nextBlock.getDefaultState());
                }
            }
        }
    }
}
