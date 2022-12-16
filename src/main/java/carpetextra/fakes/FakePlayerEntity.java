package carpetextra.fakes;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class FakePlayerEntity extends PlayerEntity {
    public static final UUID uuid = UUID.fromString("7fc6d34b-ed7e-4895-a451-8e120cefb201");
    public static final String name = "CarpetExtraFakePlayer";
    public static final GameProfile profile = new GameProfile(uuid, name);

    public FakePlayerEntity(World world, BlockPos pos) {
        super(world, pos, 0.0f, profile, null);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
