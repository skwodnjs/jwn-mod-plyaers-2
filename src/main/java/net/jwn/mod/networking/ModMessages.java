package net.jwn.mod.networking;

import net.jwn.mod.Main;
import net.jwn.mod.networking.packet.*;
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
                .networkProtocolVersion(() -> "1.0")
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

        net.messageBuilder(SetPlayerS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SetPlayerS2CPacket::new)
                .encoder(SetPlayerS2CPacket::toBytes)
                .consumerMainThread(SetPlayerS2CPacket::handle)
                .add();

        net.messageBuilder(SetTargetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SetTargetS2CPacket::new)
                .encoder(SetTargetS2CPacket::toBytes)
                .consumerMainThread(SetTargetS2CPacket::handle)
                .add();

        net.messageBuilder(ResetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ResetS2CPacket::new)
                .encoder(ResetS2CPacket::toBytes)
                .consumerMainThread(ResetS2CPacket::handle)
                .add();

        net.messageBuilder(AcceptTargetS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AcceptTargetS2CPacket::new)
                .encoder(AcceptTargetS2CPacket::toBytes)
                .consumerMainThread(AcceptTargetS2CPacket::handle)
                .add();

        net.messageBuilder(AcceptPlayerS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AcceptPlayerS2CPacket::new)
                .encoder(AcceptPlayerS2CPacket::toBytes)
                .consumerMainThread(AcceptPlayerS2CPacket::handle)
                .add();
    }



    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);

    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
