package carpetextra.helpers;

import java.util.Map;

import carpetextra.CarpetExtraSettings;
import carpetextra.mixins.FlowerPotBlock_ContentAccessorMixin;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlowerPotHelper {
    public static final Map<Block, Block> CONTENT_TO_POTTED = FlowerPotBlock_ContentAccessorMixin.getPottedBlocksMap();

    public static void updateLoadStatus(World world, BlockPos pos, Block block, boolean forceload) {
        // check if rule is enabled and block is wither rose
        if(CarpetExtraSettings.flowerPotChunkLoading && world instanceof ServerWorld && block == Blocks.WITHER_ROSE) {
            ServerWorld serverWorld = (ServerWorld) world;

            // set chunk to be force loaded
            serverWorld.setChunkForced(pos.getX() >> 4, pos.getZ() >> 4, forceload);
        }
    }


    // checks if item can be put in a flower pot
    public static boolean isPottable(Item item) {
        Block block = Block.getBlockFromItem(item);
        if(block != Blocks.AIR) {
            return CONTENT_TO_POTTED.containsKey(block);
        }
        return false;
    }

    // gets flower pot type from item
    public static FlowerPotBlock getPottedBlock(Item item) {
        return (FlowerPotBlock) CONTENT_TO_POTTED.get(Block.getBlockFromItem(item));
    }
}
