package carpetextra.utils;

import java.util.Comparator;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ChunkUtils
{
    public static final ChunkTicketType<ChunkPos> ENDER_PEARL_TICKET = ChunkTicketType.create("ender_pearl", Comparator.comparingLong(ChunkPos::toLong), 2);

    public static void addEnderPearlChunkTicket(Entity entity)
    {
        World world = entity.getEntityWorld();
        Vec3d velocity = entity.getVelocity();

        if (world instanceof ServerWorld &&
            (Math.abs(velocity.x) > 0.001 || Math.abs(velocity.z) > 0.001))
        {
            Vec3d pos = entity.getPos();
            double nx = pos.x + velocity.x;
            double nz = pos.z + velocity.z;
            ChunkPos cp = new ChunkPos(MathHelper.floor(nx) >> 4, MathHelper.floor(nz) >> 4);
            ((ServerWorld) world).getChunkManager().addTicket(ENDER_PEARL_TICKET, cp, 2, cp);
        }
    }
}
