package carpetextra.mixins;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
<<<<<<< HEAD
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
=======

>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import com.mojang.datafixers.util.Pair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

<<<<<<< HEAD
@Mixin(HoeItem.class)
public interface HoeItem_TilledBlocksAccessorMixin {
    @Accessor("TILLABLES")
    static Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> getTilledBlocks() {
=======
import net.minecraft.block.Block;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;

@Mixin(HoeItem.class)
public interface HoeItem_TilledBlocksAccessorMixin {
    @Accessor("TILLING_ACTIONS")
    static Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> getTilledBlocks() {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        throw new AssertionError();
    }
}
