package carpetextra.helpers;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.FakePlayerEntity;
import com.google.common.collect.Sets;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

public class CarpetDispenserBehaviours
{
    public static void registerCarpetBehaviours()
    {
        DispenserBlock.registerBehavior(Items.GLASS_BOTTLE, new WaterBottleDispenserBehaviour());
        DispenserBlock.registerBehavior(Items.CHEST, new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.CHEST));
        DispenserBlock.registerBehavior(Items.HOPPER, new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.HOPPER));
        DispenserBlock.registerBehavior(Items.FURNACE, new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.FURNACE));
        DispenserBlock.registerBehavior(Items.TNT, new MinecartDispenserBehaviour(AbstractMinecartEntity.Type.TNT));
        DispenserBlock.registerBehavior(Items.STICK, new TogglingDispenserBehaviour());
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

    public static class TogglingDispenserBehaviour extends ItemDispenserBehavior {

        private FakePlayerEntity player;
        private static Set<Block> toggleable = Sets.newHashSet(
            Blocks.STONE_BUTTON, Blocks.ACACIA_BUTTON, Blocks.BIRCH_BUTTON, Blocks.DARK_OAK_BUTTON,
            Blocks.JUNGLE_BUTTON, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.ACACIA_DOOR,
            Blocks.BIRCH_DOOR, Blocks.DARK_OAK_DOOR, Blocks.JUNGLE_DOOR, Blocks.OAK_DOOR,
            Blocks.SPRUCE_DOOR, Blocks.ACACIA_TRAPDOOR, Blocks.BIRCH_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR,
            Blocks.JUNGLE_TRAPDOOR, Blocks.OAK_TRAPDOOR, Blocks.SPRUCE_TRAPDOOR, Blocks.ACACIA_FENCE_GATE, 
            Blocks.BIRCH_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.OAK_FENCE_GATE, 
            Blocks.SPRUCE_FENCE_GATE, Blocks.REPEATER, Blocks.COMPARATOR, Blocks.LEVER,
            Blocks.DAYLIGHT_DETECTOR, Blocks.NOTE_BLOCK, Blocks.REDSTONE_ORE, Blocks.BELL
        );

        @Override
        protected ItemStack dispenseSilently(BlockPointer source, ItemStack stack) {
            if(!CarpetExtraSettings.dispensersToggleThings) {
                return super.dispenseSilently(source, stack);
            }

            World world = source.getWorld();
            if(player == null) player = new FakePlayerEntity(world, "toggling");
            Direction direction = (Direction) source.getBlockState().get(DispenserBlock.FACING);
            BlockPos pos = source.getBlockPos().offset(direction);
            BlockState state = world.getBlockState(pos);  
            if(toggleable.contains(state.getBlock())) {
                boolean bool = state.activate(
                    world, 
                    player,
                    Hand.MAIN_HAND,
                    new BlockHitResult(
                        new Vec3d(new Vec3i(pos.getX(), pos.getY(), pos.getZ())), 
                        direction, 
                        pos,
                        false
                    )
                );
                if(bool) return stack;
            }
            return super.dispenseSilently(source, stack);
        }
    }
}
