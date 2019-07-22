package carpetextra.utils;


import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ExtrasRegistry
{
    // Initializes Reflection
    public static final BlockEntityType<CraftingTableBlockEntity> CRAFTING_TABLE_BLOCK_ENTITY_TYPE =
            registerBlockEntity("carpet:crafting_table", CraftingTableBlockEntity::new, Blocks.CRAFTING_TABLE);

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, Supplier<T> supplier, Block... blocks) {
        return Registry.register(Registry.BLOCK_ENTITY, id, Reflection.newBlockEntityTypeBuilder(supplier, blocks).build(null));
    }

    public static void noop() {
        // initializes statics of CarpetRegistry
    }
}
