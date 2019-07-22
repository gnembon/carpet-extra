package carpetextra.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.World;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Supplier;

public class Reflection {
    public static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();


    /**
     * Calls {@link BlockEntityType.Builder#create(Supplier)} (1.14) or {@link BlockEntityType.Builder#create(Supplier, Block...)} (1.14.1)
     * @param supplier Supplier/constructor of the block entity
     * @param blocks Blocks this block entity is used for
     * @param <T> Type of the block entity
     * @return a BlockEntityType.Builder
     */
    public static <T extends BlockEntity> BlockEntityType.Builder<T> newBlockEntityTypeBuilder(Supplier<T> supplier, Block... blocks) {
        try {
            return newBlockEntityTypeBuilder_1_14(supplier);
        } catch (ReflectiveOperationException | IllegalArgumentException e1) {
            try {
                return newBlockEntityTypeBuilder_1_14_1(supplier, blocks);
            } catch (ReflectiveOperationException e2) {
                e2.addSuppressed(e1);
                throw new RuntimeException(e2);
            }
        }
    }

    private static <T extends BlockEntity> BlockEntityType.Builder<T> newBlockEntityTypeBuilder_1_14(Supplier<T> supplier) throws ReflectiveOperationException {
        Method m = Arrays.stream(BlockEntityType.Builder.class.getMethods()).filter(x -> x.getReturnType() == BlockEntityType.Builder.class).findFirst().get();
        return (BlockEntityType.Builder<T>) m.invoke(null, supplier);
    }

    private static <T extends BlockEntity> BlockEntityType.Builder<T> newBlockEntityTypeBuilder_1_14_1(Supplier<T> supplier, Block... blocks) throws ReflectiveOperationException {
        Method m = Arrays.stream(BlockEntityType.Builder.class.getMethods()).filter(x -> x.getReturnType() == BlockEntityType.Builder.class).findFirst().get();
        return (BlockEntityType.Builder<T>) m.invoke(null, supplier, blocks);
    }

    /*
    public static <T> T callPrivateConstructor(Class<T> cls) {
        try {
            Constructor<T> constr = cls.getDeclaredConstructor();
            constr.setAccessible(true);
            return constr.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ScheduleBlockRenderHandler {
        private static final String INT_WORLD = "net.minecraft.class_1937";
        private static final String INT_SCHEDULE_BLOCK_RENDER = "method_16109";
        private static final String INT_SBR_DESC_14_3 = "(Lnet/minecraft/class_2338;)V";
        private static final String INT_SBR_DESC_14_4 = "(Lnet/minecraft/class_2338;Lnet/minecraft/class_2680;Lnet/minecraft/class_2680;)V";

        private static final MethodHandle scheduleBlockRender;
        private static final boolean newScheduleBlockRender;

        static {
            Method scheduleBlockRenderMethod = getScheduleBlockRender();
            try {
                scheduleBlockRender = LOOKUP.unreflect(scheduleBlockRenderMethod);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            newScheduleBlockRender = scheduleBlockRenderMethod.getParameterTypes().length == 3;
        }

        private static Method getScheduleBlockRender() {
            MappingResolver mappings = FabricLoader.getInstance().getMappingResolver();
            try {
                String oldName = mappings.mapMethodName("intermediary", INT_WORLD, INT_SCHEDULE_BLOCK_RENDER, INT_SBR_DESC_14_3);
                return World.class.getMethod(oldName, BlockPos.class);
            } catch (ReflectiveOperationException e) {
                try {
                    String newName = mappings.mapMethodName("intermediary", INT_WORLD, INT_SCHEDULE_BLOCK_RENDER, INT_SBR_DESC_14_4);
                    return World.class.getMethod(newName, BlockPos.class, BlockState.class, BlockState.class);
                } catch (NoSuchMethodException ex) {
                    throw new IllegalStateException(ex);
                }
            }
        }
    }

    public static void scheduleBlockRender(World world, BlockPos pos, BlockState stateFrom, BlockState stateTo) {
        try {
            if (ScheduleBlockRenderHandler.newScheduleBlockRender) {
                ScheduleBlockRenderHandler.scheduleBlockRender.invokeExact(world, pos, stateFrom, stateTo);
            } else {
                ScheduleBlockRenderHandler.scheduleBlockRender.invokeExact(world, pos);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    */
}
