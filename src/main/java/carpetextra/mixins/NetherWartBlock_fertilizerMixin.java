package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetherWartBlock.class)
public abstract class NetherWartBlock_fertilizerMixin extends PlantBlock {
    protected NetherWartBlock_fertilizerMixin(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int age = state.get(NetherWartBlock.AGE);
        if (CarpetExtraSettings.blazeMeal && stack.getItem() == Items.BLAZE_POWDER && age < 3) {
            world.setBlockState(pos, this.getDefaultState().with(NetherWartBlock.AGE, age + 1), NOTIFY_LISTENERS);
            world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
            if (!player.isCreative()) stack.decrement(1);
            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
