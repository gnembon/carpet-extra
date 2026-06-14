package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CactusBlock;
=======
import net.minecraft.block.Block;
import net.minecraft.block.CactusBlock;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CactusBlock.class)
public abstract class CactusBlock_syncMixin
{
<<<<<<< HEAD
    @ModifyConstant(method = "randomTick", require = 2, constant = @Constant(intValue = Block.UPDATE_NONE))
    private int onOnScheduledTick(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return original | Block.UPDATE_CLIENTS;
=======
    @ModifyConstant(method = "randomTick", require = 2, constant = @Constant(intValue = Block.SKIP_REDRAW_AND_BLOCK_ENTITY_REPLACED_CALLBACK))
    private int onOnScheduledTick(int original)
    {
        if (CarpetExtraSettings.blockStateSyncing)
            return original | Block.NOTIFY_LISTENERS;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        else
            return original;
    }
}
