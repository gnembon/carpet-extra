package carpetextra.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockTransformerComponent;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

// Axes and hoes are driven by the block transformer component in 26.3, there are no static block maps anymore

public class BlockTransformHelper {
    public static boolean canTransform(ServerWorld world, BlockPos pos, ItemStack stack) {
        RegistryEntry<BlockTransformerComponent> holder = stack.get(DataComponentTypes.BLOCK_TRANSFORMER);
        if (holder == null) {
            return false;
        }
        Random random = world.getRandom();
        for (BlockTransformerComponent.Transform transform : holder.value().transforms()) {
            BlockState result = transform.blockStateProvider().value().getOrNull(world, random, pos);
            if (result != null) {
                return true;
            }
        }
        return false;
    }

    // stripped logs, woods, etc. have no lookup table to check against
    public static boolean isStrippedBlock(Block block) {
        return net.minecraft.registry.Registries.BLOCK.getId(block).getPath().startsWith("stripped_");
    }
}
