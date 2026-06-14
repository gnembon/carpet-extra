package carpetextra.commands;

import carpetextra.CarpetExtraSettings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
<<<<<<< HEAD
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
=======
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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
                            return 1;
                        });
        
        dispatcher.register(command);
    }
}
