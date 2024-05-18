package net.jwn.mod.networking.packet;

import net.jwn.mod.Main;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResetOpponentC2SPacket {

    public ResetOpponentC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public ResetOpponentC2SPacket(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            player.getPersistentData().remove(Main.MOD_ID + "_opponent");
        });
        return true;
    }
}
