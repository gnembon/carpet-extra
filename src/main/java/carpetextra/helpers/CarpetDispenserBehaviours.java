package carpetextra.helpers;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class CarpetDispenserBehaviours
{
    public static void registerCarpetBehaviours()
    {
        DispenserBlock.registerBehavior(Items.GLASS_BOTTLE, new WaterBottleDispenserBehaviour());
        
        DispenserBlock.registerBehavior(Items.CHEST, new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.CHEST));
        DispenserBlock.registerBehavior(Items.HOPPER, new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.HOPPER));
        DispenserBlock.registerBehavior(Items.FURNACE, new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.FURNACE));
        DispenserBlock.registerBehavior(Items.TNT, new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.TNT));
        /* This consumes too much space, must seek alternative */
        DispenserBlock.registerBehavior(Items.GOLDEN_APPLE, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.GOLDEN_CARROT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.SWEET_BERRIES, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.DANDELION, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.SEAGRASS, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.HAY_BLOCK, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.WHEAT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.CARROT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.POTATO, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.BEETROOT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.WHEAT_SEEDS, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.BEETROOT_SEEDS, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.MELON_SEEDS, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.PUMPKIN_SEEDS, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COD, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.SALMON, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.ROTTEN_FLESH, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.PORKCHOP, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.CHICKEN, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.RABBIT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.BEEF, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.MUTTON, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COOKED_PORKCHOP, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COOKED_CHICKEN, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COOKED_RABBIT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COOKED_BEEF, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COOKED_MUTTON, new FeedAnimalDispenserBehavior());

        Registry.ITEM.forEach(record -> {
            if (record instanceof MusicDiscItem)
            {
                DispenserBlock.registerBehavior(record, new DispenserRecords());
            }
        });
    }
    
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
                    world.playLevelEvent(null, 1010, pos, Item.getRawId(stack.getItem()));
                    
                    return itemStack;
                }
            }
            
            return super.dispenseSilently(source, stack);
        }
    }
    
    public static class WaterBottleDispenserBehaviour extends FallibleItemDispenserBehavior
    {
        @Override
        protected ItemStack dispenseSilently(BlockPointer source, ItemStack stack)
        {
            if (!CarpetExtraSettings.dispensersFillBottles)
            {
                return super.dispenseSilently(source, stack);
            }
            else
            {
                World world = source.getWorld();
                BlockPos pos = source.getBlockPos().offset((Direction) source.getBlockState().get(DispenserBlock.FACING));
                BlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
                Material material = state.getMaterial();
                ItemStack itemStack;
                
                if (material == Material.WATER && block instanceof FluidBlock && ((Integer) state.get(FluidBlock.LEVEL)).intValue() == 0)
                {
                    itemStack = PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER);
                }
                else
                {
                    itemStack = new ItemStack(Items.GLASS_BOTTLE);
                }
                
                stack.decrement(1);
                
                if (stack.isEmpty())
                {
                    return itemStack;
                }
                else
                {
                    if (((DispenserBlockEntity)source.getBlockEntity()).addToFirstFreeSlot(itemStack) < 0)
                    {
                        super.dispenseSilently(source, stack);
                    }
                    
                    return stack;
                }
            }
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
                List<MinecartEntity> list = source.getWorld().<MinecartEntity>getEntities(MinecartEntity.class, new Box(pos));
    
                if (list.isEmpty())
                {
                    return defaultBehaviour(source, stack);
                }
                else
                {
                    MinecartEntity minecart = list.get(0);
                    minecart.remove();
                    AbstractMinecartEntity minecartEntity = AbstractMinecartEntity.create(minecart.world, minecart.x, minecart.y, minecart.z, this.minecartType);
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
                world.playSound((PlayerEntity)null, tntEntity.x, tntEntity.y, tntEntity.z, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
            source.getWorld().playLevelEvent(1000, source.getBlockPos(), 0);
        }
    }

    public static class FeedAnimalDispenserBehavior extends ItemDispenserBehavior {

        @Override
        protected ItemStack dispenseSilently(BlockPointer source, ItemStack stack) {
            if(!CarpetExtraSettings.dispensersFeedAnimals) {
                return super.dispenseSilently(source, stack);
            }

            BlockPos pos = source.getBlockPos().offset((Direction) source.getBlockState().get(DispenserBlock.FACING));
            List<AnimalEntity> list = source.getWorld().<AnimalEntity>getEntities(AnimalEntity.class, new Box(pos));
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
            return super.dispenseSilently(source, stack);
        }
    }
}
