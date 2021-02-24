package carpetextra.utils;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;

import java.util.Comparator;

public class PistonHeadChunkLoader {
    public static final ChunkTicketType<ChunkPos> PISTON_BLOCK_TICKET = ChunkTicketType.create("piston_block", Comparator.comparingLong(ChunkPos::toLong), 6);

    public static final int obsidianHash = new Identifier("minecraft", "obsidian").hashCode();
}
