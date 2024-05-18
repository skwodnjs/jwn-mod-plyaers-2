package net.jwn.mod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.jwn.mod.Main;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.ResetS2CPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ResetOpponentCommand {
    public ResetOpponentCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("opponent").then(Commands.literal("reset").executes(this::resetOpponent)));
    }

    private int resetOpponent(CommandContext<CommandSourceStack> command) {
        if (command.getSource().getEntity() instanceof Player player) {
            if (player.getPersistentData().getBoolean(Main.MOD_ID + "_battle")) {
                // 혹시 너 전투중?
                player.sendSystemMessage(Component.translatable("error.mod_players_2.battle"));
            } else {
                // 그러면 초기화.
                if (player.getPersistentData().hasUUID(Main.MOD_ID + "_opponent")) {
                    UUID targetUUID = player.getPersistentData().getUUID(Main.MOD_ID + "_opponent");
                    if (player.level().getPlayerByUUID(targetUUID) != null) {
                        // 상대가 접속중이면 메시지 보냄
                        player.level().getPlayerByUUID(targetUUID).sendSystemMessage(
                                Component.translatable("message.mod_players_2.reset_opponent_to_target", player.getName().getString())
                        );
                    }
                    player.getPersistentData().remove(Main.MOD_ID + "_opponent");
                    ModMessages.sendToPlayer(new ResetS2CPacket(), (ServerPlayer) player);
                } else {
                    player.sendSystemMessage(Component.translatable("error.mod_players_2.opponent_empty"));
                }
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
