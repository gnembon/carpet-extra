package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Chicken.class)
public abstract class ChickenEntityMixin extends Animal
{
    protected ChickenEntityMixin(EntityType<? extends Animal> entityType_1, Level world_1)
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    {
        super(entityType_1, world_1);
    }

    @Override
<<<<<<< HEAD
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
=======
    public ActionResult interactMob(PlayerEntity player, Hand hand)
    {
        if (this.getEntityWorld() instanceof ServerWorld sw)
        {
            ItemStack stack = player.getStackInHand(hand);
            if (CarpetExtraSettings.chickenShearing && stack.getItem() == Items.SHEARS && !this.isBaby())
            {
                boolean tookDamage = this.damage(sw, player.getEntityWorld().getDamageSources().generic(), 1);
                if (tookDamage)
                {
                    this.dropItem(sw, Items.FEATHER);
                    stack.damage(1, player, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                    return ActionResult.SUCCESS;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                }
            }
        }

<<<<<<< HEAD
        return super.mobInteract(player, hand);
=======
        return super.interactMob(player, hand);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
