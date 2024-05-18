package net.jwn.mod.client;

import net.jwn.mod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ToastHudOverlay {
    public static final int TIMER = 1000; // 대략 5초?

    private static final ResourceLocation TOAST = new ResourceLocation(Main.MOD_ID, "textures/hud/toast.png");

    public static final IGuiOverlay TOAST_HUD = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        int x = -160;
        if (player.getPersistentData().getBoolean(Main.MOD_ID + "_hud")) {
            player.getPersistentData().putInt(Main.MOD_ID + "_x", player.getPersistentData().getInt(Main.MOD_ID + "_x") + 2);
            if (player.getPersistentData().getInt(Main.MOD_ID + "_x") < TIMER) {
                x = Math.min(player.getPersistentData().getInt(Main.MOD_ID + "_x") - 160, 0);
            } else {
                x = TIMER - player.getPersistentData().getInt(Main.MOD_ID + "_x");
                if (x == -160) {
                    player.getPersistentData().putBoolean(Main.MOD_ID + "_hud", false);
                    player.getPersistentData().putInt(Main.MOD_ID + "_x", 0);
                }
            }
        }
        guiGraphics.blit(TOAST, x, 0, 0, 0, 160, 32, 256, 256);

        guiGraphics.drawString(Minecraft.getInstance().font, "결투 신청!", x + 10, 7, 0x000000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, "상대: Dev", x + 10, 18, 0x000000, false);
    });
}
