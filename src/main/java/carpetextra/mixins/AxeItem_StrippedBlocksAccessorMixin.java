package carpetextra.mixins;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

@Mixin(AxeItem.class)
public interface AxeItem_StrippedBlocksAccessorMixin {
    @Accessor("STRIPPED_BLOCKS")
    static Map<Block, Block> getStrippedBlocks() {
        throw new AssertionError();
    }
}
