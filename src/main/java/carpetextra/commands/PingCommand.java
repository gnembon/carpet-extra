package carpetextra.commands;

import carpetextra.CarpetExtraSettings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import static net.minecraft.server.command.CommandManager.literal;

public class PingCommand
{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("ping").
                requires( (player) -> CarpetExtraSettings.commandPing).
                        executes( c ->
                        {
                            ServerPlayerEntity playerEntity = c.getSource().getPlayer();
                            int ping = playerEntity.pingMilliseconds;
                            playerEntity.sendSystemMessage(Text.literal("Your ping is: " + ping + " ms"), Util.NIL_UUID);
                            return 1;
                        });
        
        dispatcher.register(command);
    }
}
