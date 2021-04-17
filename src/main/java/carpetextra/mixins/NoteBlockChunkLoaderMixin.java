package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import carpetextra.logging.NoteChunkLoaderDisplay;
import carpetextra.utils.NoteBlockChunkLoader;
import net.minecraft.block.NoteBlock;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoteBlock.class)
public abstract class NoteBlockChunkLoaderMixin {

    @Inject(method = "playNote", at = @At("HEAD"))
    private void loadChunk(World world, BlockPos pos, CallbackInfo info)
    {

        if(CarpetExtraSettings.noteBlockChunkLoader)
        {
            int note = world.getBlockState(pos).get(NoteBlock.NOTE);

            if(CarpetExtraSettings.noteBlockChunkLoaderNote == -1 || note == CarpetExtraSettings.noteBlockChunkLoaderNote)
            {
                ChunkPos cp = new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4);

                ((ServerWorld) world).getChunkManager().addTicket(NoteBlockChunkLoader.ctt, cp, CarpetExtraSettings.noteBlockChunkLoaderRadius, cp);

                NoteChunkLoaderDisplay.msgPrint(pos, world);

            }
        }
    }
}
