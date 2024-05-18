package net.jwn.mod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.jwn.mod.Main;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.SetPlayerS2CPacket;
import net.jwn.mod.networking.packet.SetTargetS2CPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class SetOpponentCommand {
    public SetOpponentCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("opponent").then(Commands.literal("set").then(Commands.argument("target",
                EntityArgument.entities()).executes(command -> setOpponent(command, EntityArgument.getEntities(command, "target"))
        ))));
    }

    private int setOpponent(CommandContext<CommandSourceStack> command, Collection<? extends Entity> pTargets) {
        if (command.getSource().getEntity() instanceof Player player) {
            if (player.getPersistentData().getBoolean(Main.MOD_ID + "_battle")) {
                // 혹시 너 전투중?
                player.sendSystemMessage(Component.translatable("error.mod_players_2.battle"));
            } else if (pTargets.size() != 1) {
                // 아니면 너 여러명 지목함?
                player.sendSystemMessage(Component.translatable("error.mod_players_2.one_opponent"));
            } else if (!(pTargets.toArray()[0] instanceof Player target)) {
                // 아니면 너 플레이어 말고 다른거 지목함?
                player.sendSystemMessage(Component.translatable("error.mod_players_2.player_opponent"));
            } else if (player.equals(target)) {
                // 본인 지목한건 아니지?
                player.sendSystemMessage(Component.translatable("error.mod_players_2.cant_set_you"));
            } else {
                // 오케이 그러면 그렇게 설정해줄게.
                player.getPersistentData().putUUID(Main.MOD_ID + "_opponent", target.getUUID());
                ModMessages.sendToPlayer(new SetPlayerS2CPacket(target.getUUID()), (ServerPlayer) player);
                ModMessages.sendToPlayer(new SetTargetS2CPacket(), (ServerPlayer) target);
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
