package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(Explosion.class)
public abstract class ExplosionMixin
{
    private static ItemStack SILKY_PICK = new ItemStack(Items.DIAMOND_PICKAXE);
    
    static
    {
        SILKY_PICK.addEnchantment(Enchantments.SILK_TOUCH, 1);
    }
    
    @Inject(
            method = "affectWorld",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE,
                    target = "Lnet/minecraft/block/BlockState;getDroppedStacks(Lnet/minecraft/loot/context/LootContext$Builder;)Ljava/util/List;"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void silkyTNT(boolean bl, CallbackInfo ci, boolean bl2, ObjectArrayList objectArrayList, Iterator var4, BlockPos blockPos, BlockState blockState, Block block, BlockPos blockPos2, BlockEntity blockEntity, LootContext.Builder builder)
    {
        if (CarpetExtraSettings.silkTouchTNT)
            builder.put(LootContextParameters.TOOL, SILKY_PICK);
    }
}
