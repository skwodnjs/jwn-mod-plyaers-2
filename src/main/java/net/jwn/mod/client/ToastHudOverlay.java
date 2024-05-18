package net.jwn.mod.client;

import net.jwn.mod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ToastHudOverlay {
    public static final int TIMER = 700; // 1000에 약 5초

    private static final ResourceLocation TOAST = new ResourceLocation(Main.MOD_ID, "textures/hud/toast.png");

    public static final IGuiOverlay TOAST_HUD = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        int x = -160;
        if (player.getPersistentData().getBoolean(Main.MOD_ID + "_hud")) {
            player.getPersistentData().putInt(Main.MOD_ID + "_toast_x", player.getPersistentData().getInt(Main.MOD_ID + "_toast_x") + 2);
            if (player.getPersistentData().getInt(Main.MOD_ID + "_toast_x") < TIMER) {
                x = Math.min(player.getPersistentData().getInt(Main.MOD_ID + "_toast_x") - 160, 0);
            } else {
                x = TIMER - player.getPersistentData().getInt(Main.MOD_ID + "_toast_x");
                if (x == -160) {
                    player.getPersistentData().putBoolean(Main.MOD_ID + "_hud", false);
                    player.getPersistentData().putInt(Main.MOD_ID + "_toast_x", 0);
                }
            }
        }
        guiGraphics.blit(TOAST, x, 0, 0, 0, 160, 32, 256, 256);

        guiGraphics.drawString(Minecraft.getInstance().font, I18n.get("message.mod_players_2.lets_compete"), x + 10, 7, 0x000000, false);
//        String opponent_name = Minecraft.getInstance().level.getPlayerByUUID(player.getPersistentData().getUUID(Main.MOD_ID + "_opponent")).getName().getString();
//        guiGraphics.drawString(Minecraft.getInstance().font, I18n.get("message.mod_players_2.lets_compete_opponent", opponent_name), x + 10, 18, 0x000000, false);
    });
}
