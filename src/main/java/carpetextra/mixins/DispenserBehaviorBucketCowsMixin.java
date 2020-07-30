package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(targets = "net/minecraft/block/dispenser/DispenserBehavior$9")
public abstract class DispenserBehaviorBucketCowsMixin extends ItemDispenserBehavior
{
    @Inject(
            method = "dispenseSilently(Lnet/minecraft/util/math/BlockPointer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
            at = @At("HEAD"), cancellable = true
    )
    private void milkCow(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir)
    {
        if (!CarpetExtraSettings.dispensersMilkCows)
            return;
        World world = pointer.getWorld();
        
        if (!world.isClient)
        {
            BlockPos pos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
            List<CowEntity> cows = world.getEntitiesByType(EntityType.COW, new Box(pos), e -> e.isAlive() && !e.isBaby());
            if (!cows.isEmpty())
            {
                stack.decrement(1);
                if (stack.isEmpty())
                {
                    cir.setReturnValue(new ItemStack(Items.MILK_BUCKET));
                }
                else
                {
                    if (((DispenserBlockEntity)pointer.getBlockEntity()).addToFirstFreeSlot(new ItemStack(Items.MILK_BUCKET)) < 0)
                    {
                        this.dispense(pointer, new ItemStack(Items.MILK_BUCKET));
                    }
                    cir.setReturnValue(stack);
                }
            }
        }
    }
    
}
