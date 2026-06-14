package carpetextra.mixins;

<<<<<<< HEAD
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.item.BoatItem;
=======
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BoatItem.class)
public interface BoatItemAccessorMixin {
<<<<<<< HEAD
    @Accessor("entityType")
    EntityType<? extends AbstractBoat> getType();
=======
    @Accessor("boatEntityType")
    EntityType<? extends AbstractBoatEntity> getType();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
}
