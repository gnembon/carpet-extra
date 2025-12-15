package carpetextra.helpers;

import java.util.Map;

import carpetextra.CarpetExtraSettings;
import carpetextra.mixins.FlowerPotBlock_ContentAccessorMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;

public class FlowerPotHelper {
    public static final Map<Block, Block> CONTENT_TO_POTTED = FlowerPotBlock_ContentAccessorMixin.getPottedBlocksMap();

    public static void updateLoadStatus(Level world, BlockPos pos, Block block, boolean forceload) {
        // check if rule is enabled and block is wither rose
        if(CarpetExtraSettings.flowerPotChunkLoading && world instanceof ServerLevel && block == Blocks.WITHER_ROSE) {
            ServerLevel serverWorld = (ServerLevel) world;

            // set chunk to be force loaded
            serverWorld.setChunkForced(pos.getX() >> 4, pos.getZ() >> 4, forceload);
        }
    }


    // checks if item can be put in a flower pot
    public static boolean isPottable(Item item) {
        Block block = Block.byItem(item);
        if(block != Blocks.AIR) {
            return CONTENT_TO_POTTED.containsKey(block);
        }
        return false;
    }

    // gets flower pot type from item
    public static FlowerPotBlock getPottedBlock(Item item) {
        return (FlowerPotBlock) CONTENT_TO_POTTED.get(Block.byItem(item));
    }
}
