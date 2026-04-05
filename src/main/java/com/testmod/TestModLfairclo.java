package com.testmod;

import com.testmod.custom.packets.ModClientPackets;
import com.testmod.custom.packets.ModPackets;
import com.testmod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestModLfairclo implements ModInitializer {
	public static final String MOD_ID = "test-mod-lfairclo";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModPackets.registerPackets();
		ModClientPackets.registerClientPackets();
	}
}