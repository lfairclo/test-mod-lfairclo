package com.testmod;

import com.testmod.block.ModBlocks;
import com.testmod.custom.packets.ModClientPackets;
import com.testmod.custom.packets.ModPackets;
import com.testmod.entity.ModEntities;
import com.testmod.entity.custom.BomberPlaneEntity;
import com.testmod.item.ModItemGroups;
import com.testmod.item.ModItems;
import com.testmod.registry.ModGuns;
import com.testmod.particles.ModParticles;
import com.testmod.util.TaskScheduler;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestModLfairclo implements ModInitializer {
	public static final String MOD_ID = "test-mod-lfairclo";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {


		ModGuns.register();

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

		ModBlocks.registerModBlocks();
		ModParticles.register();

		ModPackets.registerPackets();
		ModClientPackets.registerClientPackets();

		FabricDefaultAttributeRegistry.register(ModEntities.BOMBER_PLANE, BomberPlaneEntity.createMobAttributes());

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			TaskScheduler.tick();
			//to use do something like this TaskScheduler.schedule(() -> doSecondThing(), 10);
		});

	}
}