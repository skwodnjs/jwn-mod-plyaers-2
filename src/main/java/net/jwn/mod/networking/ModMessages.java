package net.jwn.mod.networking;

import net.jwn.mod.Main;
import net.jwn.mod.networking.packet.ResetOpponentC2SPacket;
import net.jwn.mod.networking.packet.StopBattleC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Main.MOD_ID, "messages"))
                .networkProtocolVersion(() ->"1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ResetOpponentC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ResetOpponentC2SPacket::new)
                .encoder(ResetOpponentC2SPacket::toBytes)
                .consumerMainThread(ResetOpponentC2SPacket::handle)
                .add();

        net.messageBuilder(StopBattleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(StopBattleC2SPacket::new)
                .encoder(StopBattleC2SPacket::toBytes)
                .consumerMainThread(StopBattleC2SPacket::handle)
                .add();
    }



    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
