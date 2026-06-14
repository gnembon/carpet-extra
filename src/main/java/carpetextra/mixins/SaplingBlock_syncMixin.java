package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlock_syncMixin
{
    @ModifyConstant(method = "advanceTree", constant = @Constant(intValue = Block.UPDATE_NONE))
    private int onGenerate(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return Block.UPDATE_NONE | Block.UPDATE_CLIENTS;
        else
            return original;
    }
}
