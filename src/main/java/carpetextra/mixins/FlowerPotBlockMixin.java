package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(FlowerPotBlock.class)
public abstract class FlowerPotBlockMixin extends Block
{
    @Shadow @Final private static Map<Block, Block> CONTENT_TO_POTTED;

    @Shadow @Final private Block content;

    public FlowerPotBlockMixin(Settings block$Settings_1)
    {
        super(block$Settings_1);
    }

    @Inject(method = "onUse", at = @At("HEAD"))
    private void onActivate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1, CallbackInfoReturnable<Boolean> cir)
    {
        if (CarpetExtraSettings.flowerPotChunkLoading && world_1.getServer() != null && !world_1.isClient)
        {
            ItemStack stack = playerEntity_1.getStackInHand(hand_1);
            Item item = stack.getItem();
            Block block = item instanceof BlockItem ? (Block) CONTENT_TO_POTTED.getOrDefault(((BlockItem) item).getBlock(), Blocks.AIR) : Blocks.AIR;
            boolean blockNotPottable = block == Blocks.AIR;
            boolean potEmpty = this.content == Blocks.AIR;
            ServerWorld serverWorld = (ServerWorld) world_1;

            if (blockNotPottable != potEmpty && (block == Blocks.POTTED_WITHER_ROSE || this.content == Blocks.WITHER_ROSE))
            {
                // System.out.println("Chunk load status = " + potEmpty);
                serverWorld.setChunkForced(blockPos_1.getX() >> 4, blockPos_1.getZ() >> 4, potEmpty);
            }
        }
    }

    @Override
    public void onBreak(World world_1, BlockPos blockPos_1, BlockState blockState_1, PlayerEntity playerEntity_1)
    {
        if (CarpetExtraSettings.flowerPotChunkLoading && world_1.getServer() != null && !world.isClient && this.content == Blocks.WITHER_ROSE)
        {
            ServerWorld serverWorld = (ServerWorld)world_1;
            serverWorld.setChunkForced(blockPos_1.getX() >> 4, blockPos_1.getZ() >> 4, false);
        }
        super.onBreak(world_1, blockPos_1, blockState_1, playerEntity_1);
    }
}
