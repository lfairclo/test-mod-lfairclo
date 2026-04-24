package com.testmod.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class Keybinds {

    public static KeyBinding RELOAD;

    public static void register() {
        RELOAD = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.testmod.reload",
                GLFW.GLFW_KEY_R,
                "category.testmod"
        ));
    }
}