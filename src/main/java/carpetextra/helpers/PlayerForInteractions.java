package carpetextra.helpers;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import carpet.CarpetSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

/**
 * Hosts a method to get a {@link ServerPlayerEntity} to be used for interactions in a way that is more compatible
 * with mods than just using {@code null}
 */
public class PlayerForInteractions {
    /**
     * Gets a player for interactions on the given world, or {@code null} if Fabric API isn't loaded.
     * 
     * @param world The world the player should be from
     */
    public static ServerPlayerEntity get(ServerWorld world) {
        try {
            return (ServerPlayerEntity)INVOKER.invokeExact(world);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    /**
     * Holds either a Direct {@link MethodHandle} to Fabric API's FakePlayer.get(ServerWorld) method, or
     * a constant {@link MethodHandle} that simply returns {@code null} if that API isn't available, which
     * is good enough given we handle passing {@code null} to where vanilla would fail ourselves.
     */
    private static final MethodHandle INVOKER;

    static {
        MethodHandle invoker = null;
        if (FabricLoader.getInstance().isModLoaded("fabric-events-interaction-v0")) {
            try {
                Class<?> fakePlayerApi = Class.forName("net.fabricmc.fabric.api.entity.FakePlayer");
                MethodHandle handle = MethodHandles.lookup().findStatic(fakePlayerApi, "get", MethodType.methodType(fakePlayerApi, ServerWorld.class));
                // don't require their type to be used in calls
                invoker = handle.asType(MethodType.methodType(ServerPlayerEntity.class, ServerWorld.class));
            } catch (ReflectiveOperationException e) {
                CarpetSettings.LOG.warn("Failed to obtain fake player for interactions with mods from Fabric API, using fallback!", e);
            }
            
        } 
        if (invoker == null) {
            // Make a MethodHandle that returns null
            INVOKER = MethodHandles.empty(MethodType.methodType(ServerPlayerEntity.class, ServerWorld.class));
        } else {
            INVOKER = invoker;
        }
    }
}
