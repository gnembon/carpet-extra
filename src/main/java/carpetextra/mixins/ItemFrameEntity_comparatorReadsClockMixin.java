package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
=======
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

<<<<<<< HEAD
@Mixin(ItemFrame.class)
public abstract class ItemFrameEntity_comparatorReadsClockMixin extends HangingEntity
{
    public ItemFrameEntity_comparatorReadsClockMixin(EntityType<? extends ItemFrame> entityType_1, Level world_1) {
=======
@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntity_comparatorReadsClockMixin extends AbstractDecorationEntity
{
    public ItemFrameEntity_comparatorReadsClockMixin(EntityType<? extends ItemFrameEntity> entityType_1, World world_1) {
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        super(entityType_1, world_1);
    }

    @Shadow
<<<<<<< HEAD
    public abstract ItemStack getItem();

    @Inject(method = "getAnalogOutput", at =  @At("HEAD"),cancellable = true)
    private void giveClockPower(CallbackInfoReturnable<Integer> cir) {
        if(CarpetExtraSettings.comparatorReadsClock && this.getItem().getItem() == Items.CLOCK) {
            int power;
            //Every 1500 ticks, increase signal strength by one
            power = (int)(this.level().getDayTime() % 24000) / 1500;
=======
    public abstract ItemStack getHeldItemStack();

    @Inject(method = "getComparatorPower", at =  @At("HEAD"),cancellable = true)
    private void giveClockPower(CallbackInfoReturnable<Integer> cir) {
        if(CarpetExtraSettings.comparatorReadsClock && this.getHeldItemStack().getItem() == Items.CLOCK) {
            int power;
            //Every 1500 ticks, increase signal strength by one
            power = (int)(this.getEntityWorld().getTimeOfDay() % 24000) / 1500;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            //in case negative time of day every happens, make comparator output the according positive value
            if(power < 0)
                power = power + 16;
            cir.setReturnValue(power);
            cir.cancel();
        }
    }

    private boolean firstTick = true;
    public void tick() {
<<<<<<< HEAD
        if(CarpetExtraSettings.comparatorReadsClock && this.getItem().getItem() == Items.CLOCK) {
            //This doesn't handle time set commands yet
            //Every 1500 ticks, increase signal strength by one, so update comparators exactly then
            if(this.level().getDayTime() % 1500 == 0 || firstTick) {
                firstTick = false;
                if(this.pos != null) {
                    this.level().updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
=======
        if(CarpetExtraSettings.comparatorReadsClock && this.getHeldItemStack().getItem() == Items.CLOCK) {
            //This doesn't handle time set commands yet
            //Every 1500 ticks, increase signal strength by one, so update comparators exactly then
            if(this.getEntityWorld().getTimeOfDay() % 1500 == 0 || firstTick) {
                firstTick = false;
                if(this.attachedBlockPos != null) {
                    this.getEntityWorld().updateComparators(this.attachedBlockPos, Blocks.AIR);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                }
            }
        }
        super.tick();
    }
}
