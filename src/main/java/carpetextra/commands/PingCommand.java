package carpetextra.commands;

import carpetextra.CarpetExtraSettings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static net.minecraft.commands.Commands.literal;

public class PingCommand
{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        LiteralArgumentBuilder<CommandSourceStack> command = literal("ping").
                requires( (source) -> CarpetExtraSettings.commandPing && source.isPlayer()).
                        executes( c ->
                        {
                            ServerPlayer player = c.getSource().getPlayer();
                            int ping = player.connection.latency();
                            c.getSource().sendSuccess(() -> Component.literal("Your ping is: " + ping + " ms"), false);
                            return 1;
                        });
        
        dispatcher.register(command);
    }
}
