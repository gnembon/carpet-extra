package carpetextra.mixins;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;

@Mixin(FlowerPotBlock.class)
public interface FlowerPotBlock_ContentAccessorMixin {
    @Accessor("POTTED_BY_CONTENT")
    static Map<Block, Block> getPottedBlocksMap() {
        throw new AssertionError();
    }
}
