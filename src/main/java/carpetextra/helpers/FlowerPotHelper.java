package carpetextra.helpers;

import java.util.Map;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import carpetextra.CarpetExtraSettings;
import carpetextra.mixins.FlowerPotBlock_ContentAccessorMixin;
=======

import carpetextra.CarpetExtraSettings;
import carpetextra.mixins.FlowerPotBlock_ContentAccessorMixin;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

public class FlowerPotHelper {
    public static final Map<Block, Block> CONTENT_TO_POTTED = FlowerPotBlock_ContentAccessorMixin.getPottedBlocksMap();

<<<<<<< HEAD
    public static void updateLoadStatus(Level world, BlockPos pos, Block block, boolean forceload) {
        // check if rule is enabled and block is wither rose
        if(CarpetExtraSettings.flowerPotChunkLoading && world instanceof ServerLevel && block == Blocks.WITHER_ROSE) {
            ServerLevel serverWorld = (ServerLevel) world;
=======
    public static void updateLoadStatus(World world, BlockPos pos, Block block, boolean forceload) {
        // check if rule is enabled and block is wither rose
        if(CarpetExtraSettings.flowerPotChunkLoading && world instanceof ServerWorld && block == Blocks.WITHER_ROSE) {
            ServerWorld serverWorld = (ServerWorld) world;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

            // set chunk to be force loaded
            serverWorld.setChunkForced(pos.getX() >> 4, pos.getZ() >> 4, forceload);
        }
    }


    // checks if item can be put in a flower pot
    public static boolean isPottable(Item item) {
<<<<<<< HEAD
        Block block = Block.byItem(item);
=======
        Block block = Block.getBlockFromItem(item);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        if(block != Blocks.AIR) {
            return CONTENT_TO_POTTED.containsKey(block);
        }
        return false;
    }

    // gets flower pot type from item
    public static FlowerPotBlock getPottedBlock(Item item) {
<<<<<<< HEAD
        return (FlowerPotBlock) CONTENT_TO_POTTED.get(Block.byItem(item));
=======
        return (FlowerPotBlock) CONTENT_TO_POTTED.get(Block.getBlockFromItem(item));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
