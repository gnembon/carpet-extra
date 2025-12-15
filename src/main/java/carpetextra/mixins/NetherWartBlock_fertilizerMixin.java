package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetherWartBlock.class)
public abstract class NetherWartBlock_fertilizerMixin extends VegetationBlock {
    protected NetherWartBlock_fertilizerMixin(Properties settings) {
        super(settings);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int age = state.getValue(NetherWartBlock.AGE);
        if (CarpetExtraSettings.blazeMeal && stack.getItem() == Items.BLAZE_POWDER && age < 3) {
            world.setBlock(pos, this.defaultBlockState().setValue(NetherWartBlock.AGE, age + 1), UPDATE_CLIENTS);
            world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0);
            if (!player.isCreative()) stack.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }
}
