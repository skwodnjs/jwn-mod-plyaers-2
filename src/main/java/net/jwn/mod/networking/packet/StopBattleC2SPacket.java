package net.jwn.mod.networking.packet;

import net.jwn.mod.Main;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StopBattleC2SPacket {

    public StopBattleC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public StopBattleC2SPacket(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerPlayer target = (ServerPlayer) player.level().getPlayerByUUID(player.getPersistentData().getUUID(Main.MOD_ID + "_opponent"));

            player.getPersistentData().remove(Main.MOD_ID + "_opponent");
            player.getPersistentData().putBoolean(Main.MOD_ID + "_battle", false);
            player.sendSystemMessage(Component.translatable("message.mod_players.stop_battle"));
            if (target != null) {
                target.getPersistentData().remove(Main.MOD_ID + "_opponent");
                target.getPersistentData().putBoolean(Main.MOD_ID + "_battle", false);
                target.sendSystemMessage(Component.translatable("message.mod_players.stop_battle"));
            }
        });
        return true;
    }
}
