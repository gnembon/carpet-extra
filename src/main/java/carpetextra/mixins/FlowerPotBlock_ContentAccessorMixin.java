package carpetextra.mixins;

import java.util.Map;
<<<<<<< HEAD
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FlowerPotBlock.class)
public interface FlowerPotBlock_ContentAccessorMixin {
    @Accessor("POTTED_BY_CONTENT")
=======

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;

@Mixin(FlowerPotBlock.class)
public interface FlowerPotBlock_ContentAccessorMixin {
    @Accessor("CONTENT_TO_POTTED")
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    static Map<Block, Block> getPottedBlocksMap() {
        throw new AssertionError();
    }
}
