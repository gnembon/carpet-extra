package carpetextra.utils;

import carpetextra.CarpetExtraSettings;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.math.ChunkPos;
import java.util.Comparator;

public class NoteBlockChunkLoader {
    public static Comparator<ChunkPos> LongComparator = Comparator.comparingLong(ChunkPos::toLong);

    public static ChunkTicketType<ChunkPos> ctt;

    static {
        ctt = ChunkTicketType.create("note_block", NoteBlockChunkLoader.LongComparator, CarpetExtraSettings.noteBlockChunkLoaderTick);
    }

    public static void set_tick(int num){
        ctt = ChunkTicketType.create("note_block", NoteBlockChunkLoader.LongComparator, num);
    }
}