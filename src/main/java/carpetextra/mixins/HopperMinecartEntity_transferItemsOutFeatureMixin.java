package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.stream.IntStream;
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
            Container inventory = this.getOutputInventory();
            if (inventory == null) {
                return false;
            } else {
                Direction direction = getLastOutputDirection().getOpposite();
                if (this.isInventoryFull(inventory, direction)) {
                    return false;
                } else {
                    for (int i = 0; i < this.getContainerSize(); ++i) {
                        if (!this.getItem(i).isEmpty()) {
                            ItemStack itemStack_1 = this.getItem(i).copy();
                            ItemStack itemStack_2 = addItem(this, inventory, this.removeItem(i, 1), direction);
                            if (itemStack_2.isEmpty()) {
                                inventory.setChanged();
                                return true;
                            }

                            this.setItem(i, itemStack_1);
                        }
                    }

                    return false;
                }
            }
        }
        return false;
    }


    //Copied from HopperBlockEntity as it is private there
    @Unique private boolean isInventoryFull(Container inventory, Direction direction) {
        return getAvailableSlots(inventory, direction).allMatch((slot) -> {
            ItemStack stack = inventory.getItem(slot);
            return stack.getCount() >= stack.getMaxStackSize();
        });
    }
    //Copied from HopperBlockEntity as it is private there
    @Unique private static IntStream getAvailableSlots(Container inventory, Direction direction) {
        return inventory instanceof WorldlyContainer ? IntStream.of(((WorldlyContainer)inventory).getSlotsForFace(direction)) : IntStream.range(0, inventory.getContainerSize());
    }
}
