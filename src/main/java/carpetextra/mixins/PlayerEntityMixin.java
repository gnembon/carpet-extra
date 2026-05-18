package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world)
    {
        super(entityType, world);
    }
    
    @Override
    public boolean isPushable()
    {
        return !CarpetExtraSettings.disablePlayerCollision && super.isPushable();
    }
}
