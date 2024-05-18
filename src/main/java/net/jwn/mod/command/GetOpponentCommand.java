package net.jwn.mod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.jwn.mod.Main;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;
import java.util.UUID;

public class GetOpponentCommand {
    public GetOpponentCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("opponent").then(Commands.literal("get").
                then(Commands.argument("target", EntityArgument.entities()).executes(
                        command -> getOpponent(command, EntityArgument.getEntities(command, "target"))
                ))));
    }

    private int getOpponent(CommandContext<CommandSourceStack> command, Collection<? extends Entity> pTargets) {
        if (command.getSource().getEntity() instanceof Player player) {
            if (pTargets.size() != 1) {
                // 혹시 여러명 지목함?
                player.sendSystemMessage(Component.translatable("error.mod_players.one_opponent"));
            } else if (!(pTargets.toArray()[0] instanceof Player target)) {
                // 아니면 너 플레이어 말고 다른거 지목함?
                player.sendSystemMessage(Component.translatable("error.mod_players.player_opponent"));
            } else if (!target.getPersistentData().hasUUID(Main.MOD_ID + "_opponent")) {
                // 그러면 이제 get 시작. 먼저 지목한 상대가 있는지 확인.
                player.sendSystemMessage(Component.translatable("error.mod_players.opponent_empty"));
            } else {
                // 지목한 상대가 있어? 그러면 알려줄게.
                UUID opponentUUID = target.getPersistentData().getUUID(Main.MOD_ID + "_opponent");
                Player opponent = player.level().getPlayerByUUID(opponentUUID);
                if (opponent == null) {
                    // opponent 가 null 이다 -> 이 세상에 그런 사람 없다 -> 접속 중 아님
                    player.sendSystemMessage(Component.translatable("message.mod_players.get_opponent_not_in_game"));
                } else {
                    player.sendSystemMessage(
                            Component.translatable("message.mod_players.get_opponent",
                                    target.getName().getString() ,opponent.getName().getString())
                    );
                }
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
