package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WetSpongeBlock.class)
public abstract class WetSpongeBlockMixin extends Block
{

    public WetSpongeBlockMixin(Settings block$Settings_1)
    {
        super(block$Settings_1);
    }
    /*
    
    @Override
    public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1)
    {
        super.onPlaced(world_1, blockPos_1, blockState_1, livingEntity_1, itemStack_1);
        if (world_1.method_27983(). isNether() && CarpetExtraSettings.spongesDryInTheNether)//
        {
            world_1.playSound(null, blockPos_1, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world_1.random.nextFloat() - world_1.random.nextFloat()) * 0.8F);
            world_1.setBlockState(blockPos_1, Blocks.SPONGE.getDefaultState());
        }
    }*/
}
