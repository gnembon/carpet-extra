package carpetextra.logging;

import carpet.logging.LoggerRegistry;
import carpet.utils.Messenger;
import carpetextra.CarpetExtraServer;
import carpetextra.CarpetExtraSettings;
import net.minecraft.text.BaseText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class NoteChunkLoaderDisplay {

    public static void msgPrint(BlockPos pos, World blockWorld){
        if (!CarpetExtraServer.__noteBlockChunkLoader) return;
        LoggerRegistry.getLogger("noteBlockChunkLoader").log( (option, player)->{

            RegistryKey<World> world = blockWorld.getRegistryKey();

            RegistryKey<World> dim = player.world.getRegistryKey(); //getDimType
            switch (option)
            {
                case "overworld":
                    dim = World.OVERWORLD; // OW
                    break;
                case "nether":
                    dim = World.NETHER; // nether
                    break;
                case "end":
                    dim = World.END; // end
                    break;
            }

            if (world != dim)return null;

            return new BaseText[]{Messenger.c(
                    "w NoteBlock ",
                    Messenger.dblt("w", pos.getX(), pos.getY(), pos.getZ())
            )};
        });
    }
}
