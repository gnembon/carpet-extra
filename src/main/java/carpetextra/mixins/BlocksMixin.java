package carpetextra.mixins;

import carpetextra.helpers.ObsidianBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

// Credits: Skyrising
@Mixin(Blocks.class)
public class BlocksMixin
{
    @Shadow
    private static Block register(String string_1, Block block_1) {
        throw new AssertionError();
    }
    
    @Redirect(method = "<clinit>",
        slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=obsidian")),
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Lnet/minecraft/block/Block;)Lnet/minecraft/block/Block;",
            ordinal = 0))
    private static Block registerObsidian(String id, Block obsidian)
    {
        return register(id, new ObsidianBlock(Block.Settings.copy(obsidian).ticksRandomly()));
    }
}
