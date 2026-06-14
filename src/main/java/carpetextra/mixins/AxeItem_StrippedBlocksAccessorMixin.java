package carpetextra.mixins;

import java.util.Map;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AxeItem.class)
public interface AxeItem_StrippedBlocksAccessorMixin {
    @Accessor("STRIPPABLES")
    static Map<Block, Block> getStrippedBlocks() {
        throw new AssertionError();
    }
}
