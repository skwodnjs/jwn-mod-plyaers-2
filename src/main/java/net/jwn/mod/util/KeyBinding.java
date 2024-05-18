package net.jwn.mod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_TUTORIAL = "key.category.mod_players.tutorial";
    public static final String KEY_STOP_BATTLE = "key.mod_players.stop_battle";

    public static final KeyMapping STOP_BATTLE_KEY = new KeyMapping(KEY_STOP_BATTLE, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_TUTORIAL);
}
