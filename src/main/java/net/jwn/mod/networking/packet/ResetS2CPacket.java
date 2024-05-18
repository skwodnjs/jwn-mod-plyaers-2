package net.jwn.mod.networking.packet;

import net.jwn.mod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ResetS2CPacket {

    public ResetS2CPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public ResetS2CPacket(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            Minecraft.getInstance().player.getPersistentData().remove(Main.MOD_ID + "_opponent");
            Minecraft.getInstance().player.getPersistentData().putBoolean(Main.MOD_ID + "_isLoading", false);
            Minecraft.getInstance().player.getPersistentData().putInt(Main.MOD_ID + "_loading", 0);
        });
        return true;
    }
}
