package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
    public ActionResult interactMob(PlayerEntity playerEntity_1, Hand hand_1)
    {
        ItemStack stack = playerEntity_1.getStackInHand(hand_1);
        if (CarpetExtraSettings.chickenShearing && stack.getItem() == Items.SHEARS && !this.isBaby())
        {
            boolean tookDamage = this.damage(DamageSource.GENERIC, 1);
            if (tookDamage)
            {
                this.dropItem(Items.FEATHER, 1);
                stack.damage(1, (LivingEntity)playerEntity_1, ((playerEntity_1x) -> {
                    playerEntity_1x.sendToolBreakStatus(hand_1);
                }));
                return ActionResult.SUCCESS;
            }
        }
        return super.interactMob(playerEntity_1, hand_1);
    }
}
