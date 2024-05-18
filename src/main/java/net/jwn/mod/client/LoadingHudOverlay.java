package net.jwn.mod.client;

import net.jwn.mod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class LoadingHudOverlay {
    public static final int TIMER = 30;
    static final int PHASE = 3;
    static final int HEIGHT = 4;
    static final int WIDTH = 18;
    private static final ResourceLocation LOADING = new ResourceLocation(Main.MOD_ID, "textures/hud/loading.png");


    public static final IGuiOverlay LOADING_HUD = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int pX = 16;
        int pY = (screenHeight - HEIGHT) / 2;

        Player player = Minecraft.getInstance().player;
        if (player.getPersistentData().getBoolean(Main.MOD_ID + "_isLoading")) {
            System.out.println(player.getPersistentData().getInt(Main.MOD_ID + "_loading"));
            player.getPersistentData().putInt(Main.MOD_ID + "_loading", player.getPersistentData().getInt(Main.MOD_ID + "_loading") + 1);
            if (player.getPersistentData().getInt(Main.MOD_ID + "_loading") >= PHASE * TIMER) {
                player.getPersistentData().putInt(Main.MOD_ID + "_loading", 0);
            }
            int type = player.getPersistentData().getInt(Main.MOD_ID + "_loading") / TIMER;
            guiGraphics.blit(LOADING, pX, pY, WIDTH * type, 0, WIDTH, HEIGHT, 256, 256);
        }
    });
}
