package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Chicken.class)
public abstract class ChickenEntityMixin extends Animal
{
    protected ChickenEntityMixin(EntityType<? extends Animal> entityType_1, Level world_1)
    {
        super(entityType_1, world_1);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand)
    {
        if (this.level() instanceof ServerLevel sw)
        {
            ItemStack stack = player.getItemInHand(hand);
            if (CarpetExtraSettings.chickenShearing && stack.getItem() == Items.SHEARS && !this.isBaby())
            {
                boolean tookDamage = this.hurtServer(sw, player.level().damageSources().generic(), 1);
                if (tookDamage)
                {
                    this.spawnAtLocation(sw, Items.FEATHER);
                    stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.mobInteract(player, hand);
    }
}
