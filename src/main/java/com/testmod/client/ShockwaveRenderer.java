package com.testmod.client;

import com.testmod.TestModLfairclo;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

public class ShockwaveRenderer {

	// Define ONCE and reuse everywhere
	private static final Identifier SHOCKWAVE_TEXTURE =
			new Identifier("test-mod-lfairclo", "textures/vfx/shockwave.png");

	private static final RenderLayer SHOCKWAVE_LAYER =
			RenderLayer.getEntityTranslucent(SHOCKWAVE_TEXTURE);

	public static void init() {

		// REQUIRED: initialise Lodestone renderer
		RenderHandler.onClientSetup();

		// REQUIRED: register your render layer so buffers exist
		RenderHandler.addRenderType(RenderLayer.getEntityTranslucent(SHOCKWAVE_TEXTURE));

		// Render your shockwave
		WorldRenderEvents.AFTER_ENTITIES.register(context -> {
			ShockwaveClientState state = ShockwaveClientState.INSTANCE;
			if (!state.active || state.pos == null) return;

			MinecraftClient client = MinecraftClient.getInstance();
			MatrixStack matrices = context.matrixStack();

			Vec3d cam = client.gameRenderer.getCamera().getPos();
			Vec3d offset = state.pos.subtract(cam);

			matrices.push();
			matrices.translate(offset.x, offset.y, offset.z);

			VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld()
					.setFormat(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT)
					.setRenderType(RenderLayer.getEntityTranslucent(SHOCKWAVE_TEXTURE));

			float radius = getRadius(state, context.tickDelta());

			builder.setColor(255, 255, 255)
					.setAlpha(1.0f)
					.renderSphere(matrices, radius, 40, 40);

			matrices.pop();
		});

		// REQUIRED: flush Lodestone buffers every frame
		WorldRenderEvents.END.register(context -> {
			RenderHandler.endBatches();
		});
	}

	private static float getRadius(ShockwaveClientState state, float tickDelta) {
		float t = Math.min((state.ticks + tickDelta) / 60f, 1f);

		// smooth outward expansion
		float eased = 1 - (float) Math.pow(1 - t, 3);

		return eased * 100f;
	}
}