package carpetextra.mixins;

import carpet.CarpetServer;
import carpetextra.CarpetExtraSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.Block.dropStack;
import static net.minecraft.block.Block.getDroppedStacks;

@Mixin(Block.class)
public abstract class BlockMixin implements ItemConvertible {

    //carefulBreak
    @Overwrite
    /*Nullable*/
    public static void dropStacks(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack) {
        if (world instanceof ServerWorld) {
            if (CarpetExtraSettings.carefulBreak && entity instanceof PlayerEntity && entity.isInSneakingPose()) {

                getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, entity, stack).forEach((itemStack) -> {
                    Item item = itemStack.getItem();
                    int itemAmount = itemStack.getCount();
                    if (((PlayerEntity) entity).inventory.insertStack(itemStack)) {
                        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, (CarpetServer.rand.nextFloat() - CarpetServer.rand.nextFloat()) * 1.4F + 2.0F);
                        ((PlayerEntity) entity).increaseStat(Stats.PICKED_UP.getOrCreateStat(item), itemAmount);
                    } else {
                        dropStack(world, pos, itemStack);
                    }
                });
                return;
            }
            getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, entity, stack).forEach((itemStack) -> {
                dropStack(world, pos, itemStack);
            });
        }
        state.onStacksDropped(world, pos, ItemStack.EMPTY);
    }

    //carefulBreak PISTON_HEADS
    @Inject(method = "onBreak", at = @At("INVOKE"))
    private void onBreak1(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {

        if (state.getBlock() == Blocks.PISTON_HEAD && CarpetExtraSettings.carefulBreak && player.isInSneakingPose()) {
            Direction direction = ((Direction) state.get(FacingBlock.FACING)).getOpposite();
            pos = pos.offset(direction);
            BlockState blockState = world.getBlockState(pos);
            Block block = world.getBlockState(pos).getBlock();
            if (block == Blocks.PISTON || block == Blocks.STICKY_PISTON && (Boolean) blockState.get(PistonBlock.EXTENDED)) {
                dropStacks(blockState, world, pos, null, player, player.getMainHandStack());
                world.removeBlock(pos, false);
            }
        }
    }
}
