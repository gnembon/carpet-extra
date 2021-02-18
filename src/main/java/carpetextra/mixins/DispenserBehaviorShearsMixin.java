package carpetextra.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

@Mixin(ShearsDispenserBehavior.class)
public abstract class DispenserBehaviorShearsMixin
{
    @Inject(method = "tryShearBlock", at = @At("HEAD"), cancellable = true)
    private static void carvePumpkin(ServerWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if(CarpetExtraSettings.dispensersCarvePumpkins && world.getBlockState(pos).getBlock() == Blocks.PUMPKIN)
        {
            world.setBlockState(pos, Blocks.CARVED_PUMPKIN.getDefaultState(), 11);
            Block.dropStack(world, pos, new ItemStack(Items.PUMPKIN_SEEDS, 4));

            world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);

            cir.setReturnValue(true);
        }
    }
}
