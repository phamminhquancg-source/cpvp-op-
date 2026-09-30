package com.example.cpvpopt;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class OptimizerScreen extends Screen {
	private final Screen parent;

	// Values when the screen was opened (for Undo)
	private final boolean oldCrystals = CpvpConfig.optimizedCrystals;
	private final boolean oldAnchors = CpvpConfig.optimizedAnchors;
	private final boolean oldPearl = CpvpConfig.optimizedPearlCatch;
	private final double oldSpeed = CpvpConfig.itemSpeed;

	public OptimizerScreen(Screen parent) {
		super(Component.literal("Optimizer Settings"));
		this.parent = parent;
	}

	private static Component label(String name, boolean on) {
		return Component.literal(name + ": " + (on ? "ON" : "OFF"));
	}

	@Override
	protected void init() {
		int w = 220;
		int x = this.width / 2 - w / 2;
		int y = this.height / 2 - 70;

		addRenderableWidget(Button.builder(label("Optimized Crystals", CpvpConfig.optimizedCrystals), b -> {
			CpvpConfig.optimizedCrystals = !CpvpConfig.optimizedCrystals;
			b.setMessage(label("Optimized Crystals", CpvpConfig.optimizedCrystals));
		}).bounds(x, y, w, 20)
			.tooltip(Tooltip.create(Component.literal("Removes the end crystal on your client the moment you hit it, so it feels instant.")))
			.build());

		addRenderableWidget(Button.builder(label("Optimized Anchors", CpvpConfig.optimizedAnchors), b -> {
			CpvpConfig.optimizedAnchors = !CpvpConfig.optimizedAnchors;
			b.setMessage(label("Optimized Anchors", CpvpConfig.optimizedAnchors));
		}).bounds(x, y + 26, w, 20)
			.tooltip(Tooltip.create(Component.literal("Work in progress: saved in the config but does nothing yet.")))
			.build());

		addRenderableWidget(Button.builder(label("Optimized Pearl Catch", CpvpConfig.optimizedPearlCatch), b -> {
			CpvpConfig.optimizedPearlCatch = !CpvpConfig.optimizedPearlCatch;
			b.setMessage(label("Optimized Pearl Catch", CpvpConfig.optimizedPearlCatch));
		}).bounds(x, y + 52, w, 20)
			.tooltip(Tooltip.create(Component.literal("Work in progress: saved in the config but does nothing yet.")))
			.build());

		SpeedSlider slider = new SpeedSlider(x, y + 78, w, 20);
		slider.setTooltip(Tooltip.create(Component.literal(
			"Sets the optimiser speed: 1x = normal, 2x = faster, 3x = very fast, 4x = maximum. (Work in progress: saved but not applied yet.)")));
		addRenderableWidget(slider);

		int half = (w - 4) / 2;
		addRenderableWidget(Button.builder(Component.literal("Reset"), b -> {
			CpvpConfig.resetDefaults();
			rebuildWidgets();
		}).bounds(x, y + 112, half, 20).build());

		addRenderableWidget(Button.builder(Component.literal("Undo"), b -> {
			CpvpConfig.optimizedCrystals = oldCrystals;
			CpvpConfig.optimizedAnchors = oldAnchors;
			CpvpConfig.optimizedPearlCatch = oldPearl;
			CpvpConfig.itemSpeed = oldSpeed;
			rebuildWidgets();
		}).bounds(x + half + 4, y + 112, half, 20).build());

		addRenderableWidget(Button.builder(Component.literal("Done"), b -> onClose())
			.bounds(x, y + 138, w, 20).build());
	}

	@Override
	public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
		super.render(g, mouseX, mouseY, partialTick);
		g.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - 100, 0xFFFFFFFF);
	}

	@Override
	public void onClose() {
		CpvpConfig.save();
		this.minecraft.setScreen(parent);
	}

	private static class SpeedSlider extends AbstractSliderButton {
		SpeedSlider(int x, int y, int w, int h) {
			super(x, y, w, h, Component.empty(), (CpvpConfig.itemSpeed - 1.0) / 3.0);
			updateMessage();
		}

		private double speed() {
			return 1.0 + Math.round(this.value * 6.0) / 2.0; // 1.0 .. 4.0 in 0.5 steps
		}

		@Override
		protected void updateMessage() {
			setMessage(Component.literal("CPvP Item Speed: " + speed() + "x"));
		}

		@Override
		protected void applyValue() {
			CpvpConfig.itemSpeed = speed();
		}
	}
}
