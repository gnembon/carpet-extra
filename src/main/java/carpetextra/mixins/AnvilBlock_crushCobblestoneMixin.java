package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilBlock.class)
public abstract class AnvilBlock_crushCobblestoneMixin {
    @Inject(method = "onLanding", at = @At("HEAD"))
    private void crushCobblestoneLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity, CallbackInfo ci) {
        if(CarpetExtraSettings.renewableSand) {
            tryCrushCobblestone(world, pos.down());
        }
    }

    @Inject(method = "onDestroyedOnLanding", at = @At("HEAD"))
    private void crushCobblestoneLandingDestroyed(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity, CallbackInfo ci) {
        // check if anvil can fall though the block that it landed in
        if(CarpetExtraSettings.renewableSand && FallingBlock.canFallThrough(world.getBlockState(pos))) {
            tryCrushCobblestone(world, pos.down());
        }
    }
    private static void tryCrushCobblestone(World world, BlockPos pos) {
        // check if anvil landed on cobblestone block
        if(world.getBlockState(pos).isOf(Blocks.COBBLESTONE)) {
            // play block breaking sound/animation and set to sand
            world.breakBlock(pos, false);
            world.setBlockState(pos, Blocks.SAND.getDefaultState());
        }
    }
}
