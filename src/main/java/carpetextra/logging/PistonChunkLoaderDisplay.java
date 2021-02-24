package carpetextra.logging;

import carpet.logging.LoggerRegistry;
import carpet.utils.Messenger;
import carpetextra.CarpetExtraServer;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.text.BaseText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class PistonChunkLoaderDisplay {

    public static void msgPrint(BlockPos pos, World blockWorld){
        if (!CarpetExtraServer.__pistonHeadChunkLoader) return;
        LoggerRegistry.getLogger("pistonHeadChunkLoader").log( (option, player)->{

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
                    "w pistonHead ",
                    Messenger.dblt("w", pos.getX(), pos.getY(), pos.getZ())
            )};
        });
    }
}
