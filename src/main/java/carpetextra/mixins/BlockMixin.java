package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import jdk.internal.jline.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.Block.dropStack;
import static net.minecraft.block.Block.getDroppedStacks;

@Mixin(Block.class)
public abstract class BlockMixin implements ItemConvertible {

   @Inject(method = "afterBreak" , at = @At(value ="HEAD"), cancellable =true )
    private void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo ci){
        if(CarpetExtraSettings.carefulBreak && player.isInSneakingPose()){

            player.incrementStat(Stats.MINED.getOrCreateStat(state.getBlock()));
            player.addExhaustion(0.005F);

            if (world instanceof ServerWorld) {
                getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, player, stack).forEach((itemStack) -> {
                    if (!player.giveItemStack(itemStack)) {
                        dropStack(world, pos, itemStack);
                    }
                });
            }

         ci.cancel();
        }
    }
}
