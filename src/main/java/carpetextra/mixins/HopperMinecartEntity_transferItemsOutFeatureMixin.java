package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.stream.IntStream;

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

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/HopperMinecartEntity;canOperate()Z"))
    private boolean operate(HopperMinecartEntity entity) {
        boolean workDone = false;
        if(CarpetExtraSettings.hopperMinecartItemTransfer)
            workDone  = this.insert();
        workDone |= this.canOperate();
        return workDone;
    }

    private static final Vec3d upwardVec = new Vec3d(0, 1, 0).normalize().multiply(-1);
    private static final Vec3d ascending_east_offset  = new Vec3d(-1, 1,  0).normalize().multiply(-1);
    private static final Vec3d ascending_west_offset  = new Vec3d( 1, 1,  0).normalize().multiply(-1);
    private static final Vec3d ascending_north_offset = new Vec3d( 0, 1,  1).normalize().multiply(-1);
    private static final Vec3d ascending_south_offset = new Vec3d( 0, 1, -1).normalize().multiply(-1);

    private Vec3d getBlockBelowCartOffset() {
        BlockState state = this.getWorld().getBlockState(BlockPos.ofFloored(this.getPos()));
        if (state.isIn(BlockTags.RAILS)) {
            RailShape railShape = state.get(((AbstractRailBlock)state.getBlock()).getShapeProperty());
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

    private Direction outputDirection = Direction.DOWN;

    private Inventory getOutputInventory() {
        Vec3d offsetToInventory = getBlockBelowCartOffset();
        //The visual rotation point of the minecart is roughly 0.5 above its feet (determined visually ingame)
        //Search 0.5 Blocks below the feet for an inventory
        Inventory inv =  HopperBlockEntity.getInventoryAt(this.getWorld(), BlockPos.ofFloored(this.getPos().add(offsetToInventory)));

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
                outputDirection = Direction.NORTH;
            else outputDirection = Direction.DOWN;
        } else {
            outputDirection = Direction.DOWN;
        }

        return inv;
    }

    private Direction getLastOutputDirection() {
        return outputDirection;
    }

    //copied from HopperBlockEntity, (code originally taken from 1.14.4 pre 6)
    private boolean insert() {
        if (!this.isEmpty()) {
            Inventory inventory = this.getOutputInventory();
            if (inventory == null) {
                return false;
            } else {
                Direction direction = getLastOutputDirection().getOpposite();
                if (this.isInventoryFull(inventory, direction)) {
                    return false;
                } else {
                    for (int i = 0; i < this.size(); ++i) {
                        if (!this.getStack(i).isEmpty()) {
                            ItemStack itemStack_1 = this.getStack(i).copy();
                            ItemStack itemStack_2 = transfer(this, inventory, this.removeStack(i, 1), direction);
                            if (itemStack_2.isEmpty()) {
                                inventory.markDirty();
                                return true;
                            }

                            this.setStack(i, itemStack_1);
                        }
                    }

                    return false;
                }
            }
        }
        return false;
    }


    //Copied from HopperBlockEntity as it is private there
    private boolean isInventoryFull(Inventory inventory, Direction direction) {
        return getAvailableSlots(inventory, direction).allMatch((slot) -> {
            ItemStack stack = inventory.getStack(slot);
            return stack.getCount() >= stack.getMaxCount();
        });
    }
    //Copied from HopperBlockEntity as it is private there
    private static IntStream getAvailableSlots(Inventory inventory, Direction direction) {
        return inventory instanceof SidedInventory ? IntStream.of(((SidedInventory)inventory).getAvailableSlots(direction)) : IntStream.range(0, inventory.size());
    }
}
