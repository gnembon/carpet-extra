package carpetextra.commands;

import carpetextra.CarpetExtraSettings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class PingCommand
{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("ping").
                requires( (source) -> CarpetExtraSettings.commandPing && source.isExecutedByPlayer()).
                        executes( c ->
                        {
                            ServerPlayerEntity player = c.getSource().getPlayer();
                            int ping = player.networkHandler.getLatency();
                            c.getSource().sendFeedback(() -> Text.literal("Your ping is: " + ping + " ms"), false);
                            return 1;
                        });
        
        dispatcher.register(command);
    }
}
