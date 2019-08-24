package carpetextra.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class FakePlayerEntity extends PlayerEntity {

    public FakePlayerEntity(World world, String name) {
        super(world, new GameProfile(null, name));
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