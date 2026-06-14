package carpetextra.mixins;

import java.util.Map;
<<<<<<< HEAD
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AxeItem.class)
public interface AxeItem_StrippedBlocksAccessorMixin {
    @Accessor("STRIPPABLES")
=======

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

@Mixin(AxeItem.class)
public interface AxeItem_StrippedBlocksAccessorMixin {
    @Accessor("STRIPPED_BLOCKS")
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    static Map<Block, Block> getStrippedBlocks() {
        throw new AssertionError();
    }
}
