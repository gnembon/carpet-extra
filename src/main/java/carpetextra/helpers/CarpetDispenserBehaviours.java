package carpetextra.helpers;

import carpetextra.CarpetExtraSettings;
import carpetextra.utils.PlaceBlockDispenserBehavior;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.CommandBlockExecutor;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
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
                    world.syncWorldEvent(1010, pos, Item.getRawId(stack.getItem()));
                    
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
        public ItemStack dispenseSilently(BlockPointer source, ItemStack stack)
        {
            if (!CarpetExtraSettings.dispensersFillMinecarts)
            {
                return defaultBehaviour(source, stack);
            }
            else
            {
                BlockPos pos = source.getBlockPos().offset((Direction) source.getBlockState().get(DispenserBlock.FACING));
                List<MinecartEntity> list = source.getWorld().getEntitiesByClass(MinecartEntity.class, new Box(pos), EntityPredicates.VALID_ENTITY);
    
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
                    minecartEntity.setCustomName(minecart.getCustomName());
                    minecartEntity.setCustomNameVisible(minecart.isCustomNameVisible());
                    minecartEntity.setGlowing(minecart.isGlowing());
                    minecartEntity.setInvulnerable(minecart.isInvulnerable());
                    minecartEntity.setNoGravity(minecart.hasNoGravity());
                    minecartEntity.setOnGround(minecart.isOnGround());
                    minecartEntity.setAir(minecart.getAir());
                    minecartEntity.setFireTicks(minecart.getFireTicks());

                    if (minecartEntity instanceof StorageMinecartEntity)
                    {
                        try
                        {
                            Objects.requireNonNull(stack.getSubTag("BlockEntityTag"))
                                    .getList("Items",10)
                                    .iterator()
                                    .forEachRemaining(c ->
                                            ((StorageMinecartEntity)minecartEntity)
                                                    .setStack(((CompoundTag)c).getByte("Slot"),ItemStack.fromTag((CompoundTag)c)));
                        }
                        catch(Throwable ignored) { }
                    }
                    else if (minecartEntity instanceof CommandBlockMinecartEntity)
                    {
                        try
                        {
                            CompoundTag ct = Objects.requireNonNull(stack.getSubTag("BlockEntityTag"));
                            CommandBlockExecutor gcx = ((CommandBlockMinecartEntity)minecartEntity).getCommandExecutor();
                            gcx.setCommand(ct.getString("Command"));
                            gcx.setLastOutput(new LiteralText(ct.getString("LastOutput")));
                            gcx.setSuccessCount(ct.getInt("SuccessCount"));
                            gcx.shouldTrackOutput(ct.getBoolean("TrackOutput"));
                        }
                        catch(Throwable ignored) { }
                    }
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
                if (stack.getItem() instanceof BlockItem && PlaceBlockDispenserBehavior.canPlace(((BlockItem) stack.getItem()).getBlock()))
                    return PlaceBlockDispenserBehavior.getInstance().dispenseSilently(source, stack);
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
            Blocks.COMPARATOR, Blocks.LEVER, Blocks.DAYLIGHT_DETECTOR, Blocks.REDSTONE_ORE,
            Blocks.JUKEBOX, Blocks.NOTE_BLOCK, Blocks.REDSTONE_WIRE
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
            List<AnimalEntity> list = source.getWorld().getEntitiesByClass(AnimalEntity.class, new Box(pos),EntityPredicates.VALID_ENTITY);
            boolean failure = false;

            for (AnimalEntity mob : list)
            {
                if (!mob.isBreedingItem(stack)) continue;
                if (mob.getBreedingAge() == 0 && mob.canEat())
                {
                    mob.lovePlayer(null);
                }
                else if (mob.isBaby())
                {
                    mob.growUp((int) ((float) (-mob.getBreedingAge() / 20) * 0.1F), true);
                }
                else
                {
                    failure = true;
                    continue;
                }
                stack.decrement(1);
                return stack;
            }
            if(failure) return stack;
            // fix here for now - if problem shows up next time, will need to fix it one level above.
            if (stack.getItem() instanceof BlockItem && PlaceBlockDispenserBehavior.canPlace(((BlockItem) stack.getItem()).getBlock()))
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
    
    public static class MushroomStewBehavior extends ItemDispenserBehavior
    {
        @Override
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack)
        {
            if (!CarpetExtraSettings.dispensersMilkCows)
                return super.dispenseSilently(pointer, stack);
    
            World world = pointer.getWorld();
            if (!world.isClient)
            {
                BlockPos pos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                List<MooshroomEntity> mooshroom = world.getEntitiesByType(EntityType.MOOSHROOM, new Box(pos), e -> e.isAlive() && !e.isBaby());
                if (!mooshroom.isEmpty())
                {
                    stack.decrement(1);
                    if (stack.isEmpty())
                    {
                        return new ItemStack(Items.MUSHROOM_STEW);
                    }
                    else
                    {
                        if (((DispenserBlockEntity)pointer.getBlockEntity()).addToFirstFreeSlot(new ItemStack(Items.MUSHROOM_STEW)) < 0)
                        {
                            super.dispenseSilently(pointer, new ItemStack(Items.MUSHROOM_STEW));
                        }
                        return stack;
                    }
                }
            }
            return super.dispenseSilently(pointer, stack);
        }
    }
    
    public static class BlazePowderBehavior extends ItemDispenserBehavior
    {
        @Override
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack)
        {
            if (!CarpetExtraSettings.blazeMeal)
                return super.dispenseSilently(pointer, stack);
    
            World world = pointer.getWorld();
            Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
            BlockPos front = pointer.getBlockPos().offset(direction);
            BlockState state = world.getBlockState(front);
            
            if (state.getBlock() == Blocks.NETHER_WART)
            {
                int age = state.get(NetherWartBlock.AGE);
                if (age < 3)
                {
                    world.setBlockState(front, Blocks.NETHER_WART.getDefaultState().with(NetherWartBlock.AGE, age + 1), 2);
                    world.syncWorldEvent(2005, front, 0);
                    stack.decrement(1);
                    return stack;
                }
            }
            return stack;
        }
    }
}
