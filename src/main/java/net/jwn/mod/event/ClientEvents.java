package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.client.LoadingHudOverlay;
import net.jwn.mod.client.ToastHudOverlay;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.ResetOpponentC2SPacket;
import net.jwn.mod.networking.packet.StopBattleC2SPacket;
import net.jwn.mod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.OUT_KEY.consumeClick()) {
                ModMessages.sendToServer(new StopBattleC2SPacket());
            }
        }
        @SubscribeEvent
        public static void onClientPlayerLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
            ModMessages.sendToServer(new ResetOpponentC2SPacket());
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.OUT_KEY);
        }

        @SubscribeEvent
        public static void onRegisterGuiOverlaysEvent(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("toast_hud", ToastHudOverlay.TOAST_HUD);
            event.registerAboveAll("loading_hud", LoadingHudOverlay.LOADING_HUD);
        }
    }
}
