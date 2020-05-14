package carpetextra.helpers;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.PlaceBlockDispenserBehavior;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

public class CarpetDispenserBehaviours
{
    public static class DispenserRecords extends ItemDispenserBehavior
    {
        @Override
        protected ItemStack dispenseSilently(BlockPointer source, ItemStack stack)
        {
            if (!CarpetExtraSettings.dispensersPlayRecords)
                return super.dispenseSilently(source, stack);
            
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            BlockPos pos = source.getBlockPos().offset(direction);
            World world = source.getWorld();
            BlockState state = world.getBlockState(pos);
            
            if (state.getBlock() == Blocks.JUKEBOX)
            {
                JukeboxBlockEntity jukebox = (JukeboxBlockEntity) world.getBlockEntity(pos);
                if (jukebox != null)
                {
                    ItemStack itemStack = jukebox.getRecord();
                    ((JukeboxBlock) state.getBlock()).setRecord(world, pos, state, stack);
                    world.syncWorldEvent(null, 1010, pos, Item.getRawId(stack.getItem()));
                    
                    return itemStack;
                }
            }
            
            return super.dispenseSilently(source, stack);
        }
    }
    
    public static class MinecartDispenserBehaviour extends ItemDispenserBehavior
    {
        private final AbstractMinecartEntity.Type minecartType;
    
        public MinecartDispenserBehaviour(AbstractMinecartEntity.Type minecartType)
        {
            this.minecartType = minecartType;
        }
    
        @Override
        protected ItemStack dispenseSilently(BlockPointer source, ItemStack stack)
        {
            if (!CarpetExtraSettings.dispensersFillMinecarts)
            {
                return defaultBehaviour(source, stack);
            }
            else
            {
                BlockPos pos = source.getBlockPos().offset((Direction) source.getBlockState().get(DispenserBlock.FACING));
                List<MinecartEntity> list = source.getWorld().<MinecartEntity>getEntities(MinecartEntity.class, new Box(pos), null);
    
                if (list.isEmpty())
                {
                    return defaultBehaviour(source, stack);
                }
                else
                {
                    MinecartEntity minecart = list.get(0);
                    minecart.remove();
                    AbstractMinecartEntity minecartEntity = AbstractMinecartEntity.create(minecart.world, minecart.getX(), minecart.getY(), minecart.getZ(), this.minecartType);
                    minecartEntity.setVelocity(minecart.getVelocity());
                    minecartEntity.pitch = minecart.pitch;
                    minecartEntity.yaw = minecart.yaw;
                    
                    minecart.world.spawnEntity(minecartEntity);
                    stack.decrement(1);
                    return stack;
                }
            }
        }
        
        private ItemStack defaultBehaviour(BlockPointer source, ItemStack stack)
        {
            if (this.minecartType == AbstractMinecartEntity.Type.TNT)
            {
                World world = source.getWorld();
                BlockPos pos = source.getBlockPos().offset((Direction) source.getBlockState().get(DispenserBlock.FACING));
                TntEntity tntEntity = new TntEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, (LivingEntity)null);
                world.spawnEntity(tntEntity);
                world.playSound((PlayerEntity)null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
                stack.decrement(1);
                return stack;
            }
            else
            {
                return super.dispenseSilently(source, stack);
            }
        }
    
        @Override
        protected void playSound(BlockPointer source)
        {
            source.getWorld().syncWorldEvent(1000, source.getBlockPos(), 0);
        }
    }
  
    public static class TogglingDispenserBehaviour extends ItemDispenserBehavior {

        private static Set<Block> toggleable = Sets.newHashSet(
            Blocks.STONE_BUTTON, Blocks.ACACIA_BUTTON, Blocks.BIRCH_BUTTON, Blocks.DARK_OAK_BUTTON,
            Blocks.JUNGLE_BUTTON, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.REPEATER, 
            Blocks.COMPARATOR, Blocks.LEVER, Blocks.DAYLIGHT_DETECTOR, Blocks.REDSTONE_ORE, Blocks.BELL,
            Blocks.JUKEBOX
        );

        @Override
        protected ItemStack dispenseSilently(BlockPointer source, ItemStack stack) {
            if(!CarpetExtraSettings.dispensersToggleThings) {
                return super.dispenseSilently(source, stack);
            }

            World world = source.getWorld();
            Direction direction = (Direction) source.getBlockState().get(DispenserBlock.FACING);
            BlockPos pos = source.getBlockPos().offset(direction);
            BlockState state = world.getBlockState(pos);  
            if(toggleable.contains(state.getBlock())) {
                ActionResult result = state.onUse(
                    world, 
                    null,
                    Hand.MAIN_HAND,
                    new BlockHitResult(
                        Vec3d.of(pos), // flat +0
                        direction, 
                        pos,
                        false
                    )
                );
                if(result.isAccepted()) return stack; // success or consume
            }
            return super.dispenseSilently(source, stack);
        }
    }

    public static class FeedAnimalDispenserBehaviour extends ItemDispenserBehavior {

        @Override
        protected ItemStack dispenseSilently(BlockPointer source, ItemStack stack) {
            BlockPos pos = source.getBlockPos().offset((Direction) source.getBlockState().get(DispenserBlock.FACING));
            List<AnimalEntity> list = source.getWorld().getEntities(AnimalEntity.class, new Box(pos),null);
            boolean failure = false;

            for(AnimalEntity mob : list) {
                if(!mob.isBreedingItem(stack)) continue;
                if(mob.getBreedingAge() != 0 || mob.isInLove()) {
                    failure = true;
                    continue;
                }
                stack.decrement(1);
                mob.lovePlayer(null);
                return stack;
            }
            if(failure) return stack;
            // fix here for now - if problem shows up next time, will need to fix it one level above.
            if(
                    CarpetExtraSettings.dispenserPlacesBlocks &&
                    stack.getItem() instanceof BlockItem &&
                    PlaceBlockDispenserBehavior.canPlace(((BlockItem) stack.getItem()).getBlock())
            )
            {
                return PlaceBlockDispenserBehavior.getInstance().dispenseSilently(source, stack);
            }
            else
            {
                return super.dispenseSilently(source, stack);
            }
        }
    }
    
    public static class TillSoilDispenserBehaviour extends ItemDispenserBehavior
    {
        @Override
        protected ItemStack dispenseSilently(BlockPointer blockPointer_1, ItemStack itemStack_1)
        {
            if (!CarpetExtraSettings.dispensersTillSoil)
                return super.dispenseSilently(blockPointer_1, itemStack_1);
            
            World world = blockPointer_1.getWorld();
            Direction direction = blockPointer_1.getBlockState().get(DispenserBlock.FACING);
            BlockPos front = blockPointer_1.getBlockPos().offset(direction);
            BlockPos down = blockPointer_1.getBlockPos().down().offset(direction);
            BlockState frontState = world.getBlockState(front);
            BlockState downState = world.getBlockState(down);
            
            if (isFarmland(frontState) || isFarmland(downState))
                return itemStack_1;
            
            if (canDirectlyTurnToFarmland(frontState))
                world.setBlockState(front, Blocks.FARMLAND.getDefaultState());
            else if (canDirectlyTurnToFarmland(downState))
                world.setBlockState(down, Blocks.FARMLAND.getDefaultState());
            else if (frontState.getBlock() == Blocks.COARSE_DIRT)
                world.setBlockState(front, Blocks.DIRT.getDefaultState());
            else if (downState.getBlock() == Blocks.COARSE_DIRT)
                world.setBlockState(down, Blocks.DIRT.getDefaultState());
            
            if (itemStack_1.damage(1, world.random, null))
                itemStack_1.setCount(0);
            
            return itemStack_1;
        }
    
        private boolean canDirectlyTurnToFarmland(BlockState state)
        {
            return state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.GRASS_PATH;
        }
        
        private boolean isFarmland(BlockState state)
        {
            return state.getBlock() == Blocks.FARMLAND;
        }
    }
    
    public static class DragonsBreathDispenserBehaviour extends ItemDispenserBehavior
    {
        @Override
        protected ItemStack dispenseSilently(BlockPointer source, ItemStack stack)
        {
            if (!CarpetExtraSettings.dragonsBreathConvertsCobbleToEndstone)
                return super.dispenseSilently(source, stack);
            
            World world = source.getWorld();
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            BlockPos front = source.getBlockPos().offset(direction);
            BlockState state = world.getBlockState(front);
            
            if (state.getBlock() == Blocks.COBBLESTONE)
            {
                world.setBlockState(front, Blocks.END_STONE.getDefaultState());
                stack.decrement(1);
                return stack;
            }
            return super.dispenseSilently(source, stack);
        }
    }
}
