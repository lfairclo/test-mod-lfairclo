//package com.testmod.render;
//
//import com.testmod.client.ShockwaveClientState;
//import com.testmod.shaders.ModShaders;
//import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
//import net.minecraft.client.MinecraftClient;
//
//public class RenderHooks {
//
//    public static void init() {
//
//        // --- tick update ---
//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            ShockwaveClientState state = ShockwaveClientState.INSTANCE;
//
//            if (!state.active) return;
//
//            state.tick();
//
//            float tickDelta = client.getTickDelta();
//            float radius = state.getRadius(tickDelta);
//
//            ModShaders.SHOCKWAVE.setUniformValue("Origin",
//                    (float) state.origin.x,
//                    (float) state.origin.y,
//                    (float) state.origin.z
//            );
//
//            ModShaders.SHOCKWAVE.setUniformValue("Radius", radius);
//            ModShaders.SHOCKWAVE.setUniformValue("Thickness", 2.0f);
//            ModShaders.SHOCKWAVE.setUniformValue("Time", (float) state.ticks);
//        });
//
//        // --- render pass ---
//        WorldRenderEvents.END.register(context -> {
//            ShockwaveClientState state = ShockwaveClientState.INSTANCE;
//
//            if (!state.active) return;
//
//            ModShaders.SHOCKWAVE.render(context.tickDelta());
//        });
//    }
//}