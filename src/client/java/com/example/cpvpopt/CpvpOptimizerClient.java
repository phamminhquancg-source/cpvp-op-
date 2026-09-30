package com.example.cpvpopt;

import net.fabricmc.api.ClientModInitializer;

public class CpvpOptimizerClient implements ClientModInitializer {
	/** Optimized Crystals: remove the end crystal on the client as soon as you hit it. */
	public static boolean optimizedCrystals = true;

	@Override
	public void onInitializeClient() {
		// Later: config file + settings screen (Mod Menu / Cloth Config).
	}
}
