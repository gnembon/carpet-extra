package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntity_comparatorReadsClockMixin extends AbstractDecorationEntity
{
    public ItemFrameEntity_comparatorReadsClockMixin(EntityType<? extends ItemFrameEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Shadow
    public abstract ItemStack getHeldItemStack();

    @Inject(method = "getComparatorPower", at =  @At("HEAD"),cancellable = true)
    private void giveClockPower(CallbackInfoReturnable<Integer> cir) {
        if(CarpetExtraSettings.comparatorReadsClock && this.getHeldItemStack().getItem() == Items.CLOCK) {
            int power;
            //Every 1500 ticks, increase signal strength by one
            power = (int)(this.world.getTimeOfDay() % 24000) / 1500;
            //in case negative time of day every happens, make comparator output the according positive value
            if(power < 0)
                power = power + 16;
            cir.setReturnValue(power);
            cir.cancel();
        }
    }

    private boolean firstTick = true;
    public void tick() {
        if(CarpetExtraSettings.comparatorReadsClock && this.getHeldItemStack().getItem() == Items.CLOCK) {
            //This doesn't handle time set commands yet

            //Every 1500 ticks, increase signal strength by one, so update comparators exactly then
            if(this.world.getTimeOfDay() % 1500 == 0 || firstTick) {
                firstTick = false;
                if(this.attachmentPos != null) {
                    this.world.updateComparators(this.attachmentPos, Blocks.AIR);
                }
            }
        }
        super.tick();
    }

    @Inject(method = "Lnet/minecraft/entity/decoration/ItemFrameEntity;setHeldItemStack(Lnet/minecraft/item/ItemStack;Z)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/World;updateComparators(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V"), cancellable = true)
    private void onStackChangeUpdateComparatorDownwards(ItemStack value, boolean update, CallbackInfo ci) {
        if (CarpetExtraSettings.comparatorBetterItemFrames.id == 3) {
            this.world.updateComparators(this.attachmentPos.offset(this.getHorizontalFacing().getOpposite()), Blocks.AIR);
        }
    }

    @Inject(method = "Lnet/minecraft/entity/decoration/ItemFrameEntity;setRotation(IZ)V", at = @At(value = "INVOKE", target="Lnet/minecraft/world/World;updateComparators(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V"), cancellable = true)
    private void onRotationUpdateComparatorDownwards(int value, boolean bl, CallbackInfo ci) {
        if (CarpetExtraSettings.comparatorBetterItemFrames.id == 3) {
            this.world.updateComparators(this.attachmentPos.offset(this.getHorizontalFacing().getOpposite()), Blocks.AIR);
        }
    }
}
