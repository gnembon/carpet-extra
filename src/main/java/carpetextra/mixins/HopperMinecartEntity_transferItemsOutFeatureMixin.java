package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
<<<<<<< HEAD
=======
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.entity.vehicle.StorageMinecartEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.stream.IntStream;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.world.level.block.entity.HopperBlockEntity.addItem;

@Mixin(MinecartHopper.class)
public abstract class HopperMinecartEntity_transferItemsOutFeatureMixin extends AbstractMinecartContainer implements Hopper
{
    @Shadow
    public abstract boolean suckInItems();

    @Shadow public abstract int getContainerSize();

    public HopperMinecartEntity_transferItemsOutFeatureMixin(EntityType<? extends MinecartHopper> type, Level world) {
        super(type, world);
    }

    @Redirect(method = "tryConsumeItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/MinecartHopper;suckInItems()Z"))
    private boolean operate(MinecartHopper entity) {
        boolean workDone = false;
        if(CarpetExtraSettings.hopperMinecartItemTransfer)
            workDone  = this.insert();
        workDone |= this.suckInItems();
        return workDone;
    }

    @Unique private static final Vec3 upwardVec = new Vec3(0, 1, 0).normalize().scale(-1);
    @Unique private static final Vec3 ascending_east_offset  = new Vec3(-1, 1, 0).normalize().scale(-1);
    @Unique private static final Vec3 ascending_west_offset  = new Vec3( 1, 1,  0).normalize().scale(-1);
    @Unique private static final Vec3 ascending_north_offset = new Vec3( 0, 1,  1).normalize().scale(-1);
    @Unique private static final Vec3 ascending_south_offset = new Vec3( 0, 1, -1).normalize().scale(-1);

    @Unique private Vec3 getBlockBelowCartOffset() {
        BlockState state = this.level().getBlockState(BlockPos.containing(this.position()));
        if (state.is(BlockTags.RAILS)) {
            RailShape railShape = state.getValue(((BaseRailBlock)state.getBlock()).getShapeProperty());
=======

import static net.minecraft.block.entity.HopperBlockEntity.transfer;

@Mixin(HopperMinecartEntity.class)
public abstract class HopperMinecartEntity_transferItemsOutFeatureMixin extends StorageMinecartEntity implements Hopper
{
    @Shadow
    public abstract boolean canOperate();

    @Shadow public abstract int size();

    public HopperMinecartEntity_transferItemsOutFeatureMixin(EntityType<? extends HopperMinecartEntity> type, World world) {
        super(type, world);
    }

    @Redirect(method = "tickHopper", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/HopperMinecartEntity;canOperate()Z"))
    private boolean operate(HopperMinecartEntity entity) {
        boolean workDone = false;
        if(CarpetExtraSettings.hopperMinecartItemTransfer)
            workDone  = this.insert();
        workDone |= this.canOperate();
        return workDone;
    }

    @Unique private static final Vec3d upwardVec = new Vec3d(0, 1, 0).normalize().multiply(-1);
    @Unique private static final Vec3d ascending_east_offset  = new Vec3d(-1, 1, 0).normalize().multiply(-1);
    @Unique private static final Vec3d ascending_west_offset  = new Vec3d( 1, 1,  0).normalize().multiply(-1);
    @Unique private static final Vec3d ascending_north_offset = new Vec3d( 0, 1,  1).normalize().multiply(-1);
    @Unique private static final Vec3d ascending_south_offset = new Vec3d( 0, 1, -1).normalize().multiply(-1);

    @Unique private Vec3d getBlockBelowCartOffset() {
        BlockState state = this.getEntityWorld().getBlockState(BlockPos.ofFloored(this.getEntityPos()));
        if (state.isIn(BlockTags.RAILS)) {
            RailShape railShape = state.get(((AbstractRailBlock)state.getBlock()).getShapeProperty());
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            return switch (railShape) {
                case ASCENDING_EAST -> ascending_east_offset;
                case ASCENDING_WEST -> ascending_west_offset;
                case ASCENDING_NORTH -> ascending_north_offset;
                case ASCENDING_SOUTH -> ascending_south_offset;
                default -> upwardVec;
            };
        }
        return upwardVec;
    }

    @Unique private Direction outputDirection = Direction.DOWN;

<<<<<<< HEAD
    @Unique private Container getOutputInventory() {
        Vec3 offsetToInventory = getBlockBelowCartOffset();
        //The visual rotation point of the minecart is roughly 0.5 above its feet (determined visually ingame)
        //Search 0.5 Blocks below the feet for an inventory
        Container inv =  HopperBlockEntity.getContainerAt(this.level(), BlockPos.containing(this.position().add(offsetToInventory)));

        //There is probably a way nicer way to determine the access side of the target inventory
        if (inv instanceof BlockEntity be) {
            BlockPos pos = be.getBlockPos();
            if (pos.getY() < Mth.floor(this.getY()))
                outputDirection = Direction.DOWN;
            else if(pos.getX() > Mth.floor(this.getX()))
                outputDirection = Direction.EAST;
            else if(pos.getX() < Mth.floor(this.getX()))
                outputDirection = Direction.WEST;
            else if(pos.getZ() > Mth.floor(this.getZ()))
                outputDirection = Direction.SOUTH;
            else if(pos.getZ() < Mth.floor(this.getZ()))
=======
    @Unique private Inventory getOutputInventory() {
        Vec3d offsetToInventory = getBlockBelowCartOffset();
        //The visual rotation point of the minecart is roughly 0.5 above its feet (determined visually ingame)
        //Search 0.5 Blocks below the feet for an inventory
        Inventory inv =  HopperBlockEntity.getInventoryAt(this.getEntityWorld(), BlockPos.ofFloored(this.getEntityPos().add(offsetToInventory)));

        //There is probably a way nicer way to determine the access side of the target inventory
        if (inv instanceof BlockEntity be) {
            BlockPos pos = be.getPos();
            if (pos.getY() < MathHelper.floor(this.getY()))
                outputDirection = Direction.DOWN;
            else if(pos.getX() > MathHelper.floor(this.getX()))
                outputDirection = Direction.EAST;
            else if(pos.getX() < MathHelper.floor(this.getX()))
                outputDirection = Direction.WEST;
            else if(pos.getZ() > MathHelper.floor(this.getZ()))
                outputDirection = Direction.SOUTH;
            else if(pos.getZ() < MathHelper.floor(this.getZ()))
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                outputDirection = Direction.NORTH;
            else outputDirection = Direction.DOWN;
        } else {
            outputDirection = Direction.DOWN;
        }

        return inv;
    }

    @Unique private Direction getLastOutputDirection() {
        return outputDirection;
    }

    //copied from HopperBlockEntity, (code originally taken from 1.14.4 pre 6)
    @Unique private boolean insert() {
        if (!this.isEmpty()) {
<<<<<<< HEAD
            Container inventory = this.getOutputInventory();
=======
            Inventory inventory = this.getOutputInventory();
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            if (inventory == null) {
                return false;
            } else {
                Direction direction = getLastOutputDirection().getOpposite();
                if (this.isInventoryFull(inventory, direction)) {
                    return false;
                } else {
<<<<<<< HEAD
                    for (int i = 0; i < this.getContainerSize(); ++i) {
                        if (!this.getItem(i).isEmpty()) {
                            ItemStack itemStack_1 = this.getItem(i).copy();
                            ItemStack itemStack_2 = addItem(this, inventory, this.removeItem(i, 1), direction);
                            if (itemStack_2.isEmpty()) {
                                inventory.setChanged();
                                return true;
                            }

                            this.setItem(i, itemStack_1);
=======
                    for (int i = 0; i < this.size(); ++i) {
                        if (!this.getStack(i).isEmpty()) {
                            ItemStack itemStack_1 = this.getStack(i).copy();
                            ItemStack itemStack_2 = transfer(this, inventory, this.removeStack(i, 1), direction);
                            if (itemStack_2.isEmpty()) {
                                inventory.markDirty();
                                return true;
                            }

                            this.setStack(i, itemStack_1);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                        }
                    }

                    return false;
                }
            }
        }
        return false;
    }


    //Copied from HopperBlockEntity as it is private there
<<<<<<< HEAD
    @Unique private boolean isInventoryFull(Container inventory, Direction direction) {
        return getAvailableSlots(inventory, direction).allMatch((slot) -> {
            ItemStack stack = inventory.getItem(slot);
            return stack.getCount() >= stack.getMaxStackSize();
        });
    }
    //Copied from HopperBlockEntity as it is private there
    @Unique private static IntStream getAvailableSlots(Container inventory, Direction direction) {
        return inventory instanceof WorldlyContainer ? IntStream.of(((WorldlyContainer)inventory).getSlotsForFace(direction)) : IntStream.range(0, inventory.getContainerSize());
=======
    @Unique private boolean isInventoryFull(Inventory inventory, Direction direction) {
        return getAvailableSlots(inventory, direction).allMatch((slot) -> {
            ItemStack stack = inventory.getStack(slot);
            return stack.getCount() >= stack.getMaxCount();
        });
    }
    //Copied from HopperBlockEntity as it is private there
    @Unique private static IntStream getAvailableSlots(Inventory inventory, Direction direction) {
        return inventory instanceof SidedInventory ? IntStream.of(((SidedInventory)inventory).getAvailableSlots(direction)) : IntStream.range(0, inventory.size());
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
}
