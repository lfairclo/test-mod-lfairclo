package com.testmod.shaders;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.util.Identifier;

public class ModShaders {
    public static ManagedShaderEffect SHOCKWAVE;

    public static void init() {
        SHOCKWAVE = ShaderEffectManager.getInstance()
                .manage(new Identifier("yourmod", "shaders/post/shockwave.json"));
    }
}
