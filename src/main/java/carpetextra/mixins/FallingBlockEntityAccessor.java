package carpetextra.mixins;

<<<<<<< HEAD
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
=======
import net.minecraft.block.BlockState;
import net.minecraft.entity.FallingBlockEntity;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockEntityAccessor {
    @Accessor
    void setBlockState(BlockState state);
}
