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

public class AcceptCommand {
    public AcceptCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("accept").then(Commands.argument("target", EntityArgument.entities()).executes(
                command -> accept(command, EntityArgument.getEntities(command, "target"))
        )));
    }

    private int accept(CommandContext<CommandSourceStack> command, Collection<? extends Entity> pTargets) {
        if (command.getSource().getEntity() instanceof Player player) {
            if (player.getPersistentData().getBoolean(Main.MOD_ID + "_battle")) {
                // 혹시 너 전투중?
                player.sendSystemMessage(Component.translatable("error.mod_players.battle"));
            } else if (pTargets.size() != 1) {
                // 아니면 너 여러명 지목함?
                player.sendSystemMessage(Component.translatable("error.mod_players.one_opponent"));
            } else if (!(pTargets.toArray()[0] instanceof Player target)) {
                // 아니면 너 플레이어 말고 다른거 지목함?
                player.sendSystemMessage(Component.translatable("error.mod_players.player_opponent"));
            } else if (player.equals(target)) {
                // 본인 지목한건 아니지?
                player.sendSystemMessage(Component.translatable("error.mod_players.cant_set_you"));
            } else {
                // 오케이 그러면 상대가 널 지목했는지 확인해볼게.
                if (!target.getPersistentData().hasUUID(Main.MOD_ID + "_opponent")) {
                    player.sendSystemMessage(Component.translatable("message.mod_players.accept_failed"));
                } else if (!target.getPersistentData().getUUID(Main.MOD_ID + "_opponent").equals(player.getUUID())) {
                    player.sendSystemMessage(Component.translatable("message.mod_players.accept_failed"));
                } else {
                    // accept
                    target.getPersistentData().putBoolean(Main.MOD_ID + "_battle", true);
                    player.getPersistentData().putBoolean(Main.MOD_ID + "_battle", true);

                    player.getPersistentData().putUUID(Main.MOD_ID + "_opponent", target.getUUID());

                    target.sendSystemMessage(Component.translatable("message.mod_players.accept_target", player.getName().getString()));
                    player.sendSystemMessage(Component.translatable("message.mod_players.accept_player", target.getName().getString()));
                }
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
