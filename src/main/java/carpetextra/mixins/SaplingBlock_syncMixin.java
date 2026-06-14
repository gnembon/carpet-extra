package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
=======
import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlock_syncMixin
{
<<<<<<< HEAD
    @ModifyConstant(method = "advanceTree", constant = @Constant(intValue = Block.UPDATE_NONE))
    private int onGenerate(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return Block.UPDATE_NONE | Block.UPDATE_CLIENTS;
=======
    @ModifyConstant(method = "generate", constant = @Constant(intValue = Block.SKIP_REDRAW_AND_BLOCK_ENTITY_REPLACED_CALLBACK))
    private int onGenerate(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return Block.SKIP_REDRAW_AND_BLOCK_ENTITY_REPLACED_CALLBACK | Block.NOTIFY_LISTENERS;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        else
            return original;
    }
}
