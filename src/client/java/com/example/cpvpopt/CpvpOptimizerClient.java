package com.example.cpvpopt;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class CpvpOptimizerClient implements ClientModInitializer {
	private static KeyMapping openSettingsKey;

	@Override
	public void onInitializeClient() {
		CpvpConfig.load();

		KeyMapping.Category category = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath("cpvpoptimizer", "main"));
		openSettingsKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
			"key.cpvpoptimizer.open", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DELETE, category));

		ClientTickEvents.END_CLIENT_TICK.register(mc -> {
			while (openSettingsKey.consumeClick()) {
				mc.setScreen(new OptimizerScreen(mc.screen));
			}
		});
	}
}
