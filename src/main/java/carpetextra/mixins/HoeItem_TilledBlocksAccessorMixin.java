package carpetextra.mixins;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.mojang.datafixers.util.Pair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;

@Mixin(HoeItem.class)
public interface HoeItem_TilledBlocksAccessorMixin {
    @Accessor("TILLING_ACTIONS")
    static Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> getTilledBlocks() {
        throw new AssertionError();
    }
}
