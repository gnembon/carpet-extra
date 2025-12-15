package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrame.class)
public abstract class ItemFrameEntity_comparatorReadsClockMixin extends HangingEntity
{
    public ItemFrameEntity_comparatorReadsClockMixin(EntityType<? extends ItemFrame> entityType_1, Level world_1) {
        super(entityType_1, world_1);
    }

    @Shadow
    public abstract ItemStack getItem();

    @Inject(method = "getAnalogOutput", at =  @At("HEAD"),cancellable = true)
    private void giveClockPower(CallbackInfoReturnable<Integer> cir) {
        if(CarpetExtraSettings.comparatorReadsClock && this.getItem().getItem() == Items.CLOCK) {
            int power;
            //Every 1500 ticks, increase signal strength by one
            power = (int)(this.level().getDayTime() % 24000) / 1500;
            //in case negative time of day every happens, make comparator output the according positive value
            if(power < 0)
                power = power + 16;
            cir.setReturnValue(power);
            cir.cancel();
        }
    }

    private boolean firstTick = true;
    public void tick() {
        if(CarpetExtraSettings.comparatorReadsClock && this.getItem().getItem() == Items.CLOCK) {
            //This doesn't handle time set commands yet
            //Every 1500 ticks, increase signal strength by one, so update comparators exactly then
            if(this.level().getDayTime() % 1500 == 0 || firstTick) {
                firstTick = false;
                if(this.pos != null) {
                    this.level().updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
                }
            }
        }
        super.tick();
    }
}
