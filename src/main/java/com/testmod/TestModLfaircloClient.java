package com.testmod;


import com.testmod.client.Keybinds;
import com.testmod.client.ReloadKeybind;
import com.testmod.client.ShockwaveClientState;
import com.testmod.client.ShockwaveRenderer;
import com.testmod.entity.ModEntities;
import com.testmod.entity.client.BomberPlaneModel;
import com.testmod.entity.client.BomberPlaneRenderer;
import com.testmod.entity.client.ModModelLayers;
import com.testmod.particles.ModParticles;
import com.testmod.shaders.ModShaders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class TestModLfaircloClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModShaders.init();
//        ShockwaveRenderer.init();
        ParticleFactoryRegistry.getInstance().register(
                ModParticles.NEWSMOKE,
                LodestoneWorldParticleType.Factory::new
        );
        ParticleFactoryRegistry.getInstance().register(
                ModParticles.EXPLOSION,
                LodestoneWorldParticleType.Factory::new
        );
        ParticleFactoryRegistry.getInstance().register(
                ModParticles.EXPLOSION_BACKROUND,
                LodestoneWorldParticleType.Factory::new
        );
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ShockwaveClientState state = ShockwaveClientState.INSTANCE;

            if (!state.active) return;

            state.ticks++;

            if (state.ticks > 40) {
                state.reset();
            }
        });
        Keybinds.register();
        ReloadKeybind.init();

        EntityRendererRegistry.register(ModEntities.BOMBER_PLANE, BomberPlaneRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BOMBER_PLANE, BomberPlaneModel::getTexturedModelData);

    }

}
