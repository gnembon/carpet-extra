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
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.stream.IntStream;

import static net.minecraft.block.entity.HopperBlockEntity.transfer;

@Mixin(HopperMinecartEntity.class)
public abstract class HopperMinecartEntity_transferItemsOutFeatureMixin extends StorageMinecartEntity implements Hopper
{
    @Shadow @Final @Mutable
    private BlockPos currentBlockPos;
    @Shadow
    public abstract boolean canOperate();

    public HopperMinecartEntity_transferItemsOutFeatureMixin(EntityType<? extends HopperMinecartEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
        this.currentBlockPos = BlockPos.ORIGIN;
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/HopperMinecartEntity;canOperate()Z"))
    private boolean operate(HopperMinecartEntity hopperMinecartEntity){
        boolean workDone = false;
        if(CarpetExtraSettings.hopperMinecartItemTransfer)
            workDone  = this.insert();
        workDone |= this.canOperate();
        return workDone;
    }


    private static final Vec3d upwardVec = new Vec3d(0,1,0).normalize().multiply(-1);
    private static final Vec3d ascending_east_offset = new Vec3d(-1,1, 0).normalize().multiply(-1);
    private static final Vec3d ascending_west_offset = new Vec3d( 1,1, 0).normalize().multiply(-1);
    private static final Vec3d ascending_north_offset= new Vec3d( 0,1, 1).normalize().multiply(-1);
    private static final Vec3d ascending_south_offset= new Vec3d( 0,1,-1).normalize().multiply(-1);

    private Vec3d getBlockBelowCartOffset(){
        BlockState blockState_1 = this.world.getBlockState(new BlockPos(MathHelper.floor(this.x), MathHelper.floor(this.y), MathHelper.floor(this.z)));
        if (blockState_1.matches(BlockTags.RAILS)) {
            RailShape railShape = (RailShape)blockState_1.get(((AbstractRailBlock)blockState_1.getBlock()).getShapeProperty());
            switch (railShape){
                case ASCENDING_EAST:
                    return ascending_east_offset;
                case ASCENDING_WEST:
                    return ascending_west_offset;
                case ASCENDING_NORTH:
                    return ascending_north_offset;
                case ASCENDING_SOUTH:
                    return ascending_south_offset;
                default:
                    return upwardVec;
            }
        }
        return upwardVec;
    }

    private Direction outputDirection = Direction.DOWN;

    private Inventory getOutputInventory() {
        Vec3d offsetToInventory = getBlockBelowCartOffset();
        //The visual rotation point of the minecart is roughly 0.5 above its feet (determined visually ingame)
        //Search 0.5 Blocks below the feet for an inventory
        Inventory inv =  HopperBlockEntity.getInventoryAt(this.world, this.x + offsetToInventory.x, this.y + 0.5 + offsetToInventory.y, this.z + offsetToInventory.z);

        //There is probably a way nicer way to determine the access side of the target inventory
        if(inv instanceof BlockEntity){
            BlockPos pos = ((BlockEntity) inv).getPos();
            if(pos.getY() < MathHelper.floor(this.y))
                outputDirection = Direction.DOWN;
            else if(pos.getX() > MathHelper.floor(this.x))
                outputDirection = Direction.EAST;
            else if(pos.getX() < MathHelper.floor(this.x))
                outputDirection = Direction.WEST;
            else if(pos.getZ() > MathHelper.floor(this.z))
                outputDirection = Direction.SOUTH;
            else if(pos.getZ() < MathHelper.floor(this.z))
                outputDirection = Direction.NORTH;
            else outputDirection = Direction.DOWN;
        }else
            outputDirection = Direction.DOWN;



        return inv;
    }

    private Direction getLastOutputDirection() {
        return outputDirection;
    }

    //copied from HopperBlockEntity, (code originally taken from 1.14.4 pre 6)
    private boolean insert(){
        if(!this.isInvEmpty()){
            Inventory inventory_1 = this.getOutputInventory();
            if (inventory_1 == null) {
                return false;
            } else {
                Direction direction_1 = getLastOutputDirection().getOpposite();
                if (this.isInventoryFull(inventory_1, direction_1)) {
                    return false;
                } else {
                    for(int int_1 = 0; int_1 < this.getInvSize(); ++int_1) {
                        if (!this.getInvStack(int_1).isEmpty()) {
                            ItemStack itemStack_1 = this.getInvStack(int_1).copy();
                            ItemStack itemStack_2 = transfer(this, inventory_1, this.takeInvStack(int_1, 1), direction_1);
                            if (itemStack_2.isEmpty()) {
                                inventory_1.markDirty();
                                return true;
                            }

                            this.setInvStack(int_1, itemStack_1);
                        }
                    }

                    return false;
                }
            }
        }
        return false;
    }


    //Copied from HopperBlockEntity as it is private there
    private boolean isInventoryFull(Inventory inventory_1, Direction direction_1) {
        return getAvailableSlots(inventory_1, direction_1).allMatch((int_1) -> {
            ItemStack itemStack_1 = inventory_1.getInvStack(int_1);
            return itemStack_1.getCount() >= itemStack_1.getMaxCount();
        });
    }
    //Copied from HopperBlockEntity as it is private there
    private static IntStream getAvailableSlots(Inventory inventory_1, Direction direction_1) {
        return inventory_1 instanceof SidedInventory ? IntStream.of(((SidedInventory)inventory_1).getInvAvailableSlots(direction_1)) : IntStream.range(0, inventory_1.getInvSize());
    }


}
