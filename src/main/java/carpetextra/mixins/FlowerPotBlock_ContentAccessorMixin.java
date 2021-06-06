package carpetextra.mixins;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;

@Mixin(FlowerPotBlock.class)
public interface FlowerPotBlock_ContentAccessorMixin {
    @Accessor("CONTENT_TO_POTTED")
    static Map<Block, Block> getPottedBlocksMap() {
        throw new AssertionError();
    }
}
