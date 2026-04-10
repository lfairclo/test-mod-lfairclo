package com.testmod;

import com.testmod.block.ModBlocks;
import com.testmod.custom.packets.ModClientPackets;
import com.testmod.custom.packets.ModPackets;
import com.testmod.item.ModItemGroups;
import com.testmod.item.ModItems;
import com.testmod.util.TaskScheduler;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestModLfairclo implements ModInitializer {
	public static final String MOD_ID = "test-mod-lfairclo";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

		ModBlocks.registerModBlocks();
		ModPackets.registerPackets();
		ModClientPackets.registerClientPackets();

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			TaskScheduler.tick();
			//to use do something like this TaskScheduler.schedule(() -> doSecondThing(), 10);
		});

	}
}