package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlock_syncMixin
{
    @ModifyConstant(method = "generate", constant = @Constant(intValue = Block.SKIP_REDRAW_AND_BLOCK_ENTITY_REPLACED_CALLBACK))
    private int onGenerate(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return Block.SKIP_REDRAW_AND_BLOCK_ENTITY_REPLACED_CALLBACK | Block.NOTIFY_LISTENERS;
        else
            return original;
    }
}
