package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity
{
    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType_1, World world_1)
    {
        super(entityType_1, world_1);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand)
    {
        if (this.getWorld() instanceof ServerWorld sw)
        {
            ItemStack stack = player.getStackInHand(hand);
            if (CarpetExtraSettings.chickenShearing && stack.getItem() == Items.SHEARS && !this.isBaby())
            {
                boolean tookDamage = this.damage(sw, player.getWorld().getDamageSources().generic(), 1);
                if (tookDamage)
                {
                    this.dropItem(sw, Items.FEATHER);
                    stack.damage(1, player, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return super.interactMob(player, hand);
    }
}
