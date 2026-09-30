package com.example.cpvpopt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public class CpvpConfig {
	public static boolean optimizedCrystals = true;
	public static boolean optimizedAnchors = true;
	public static boolean optimizedPearlCatch = true;
	public static double itemSpeed = 1.0;

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private static Path file() {
		return FabricLoader.getInstance().getConfigDir().resolve("cpvpoptimizer.json");
	}

	public static void resetDefaults() {
		optimizedCrystals = true;
		optimizedAnchors = true;
		optimizedPearlCatch = true;
		itemSpeed = 1.0;
	}

	public static void load() {
		try {
			Path f = file();
			if (!Files.exists(f)) return;
			JsonObject o = GSON.fromJson(Files.readString(f), JsonObject.class);
			if (o == null) return;
			if (o.has("optimizedCrystals")) optimizedCrystals = o.get("optimizedCrystals").getAsBoolean();
			if (o.has("optimizedAnchors")) optimizedAnchors = o.get("optimizedAnchors").getAsBoolean();
			if (o.has("optimizedPearlCatch")) optimizedPearlCatch = o.get("optimizedPearlCatch").getAsBoolean();
			if (o.has("itemSpeed")) itemSpeed = Math.max(1.0, Math.min(4.0, o.get("itemSpeed").getAsDouble()));
		} catch (Exception ignored) {
		}
	}

	public static void save() {
		try {
			JsonObject o = new JsonObject();
			o.addProperty("optimizedCrystals", optimizedCrystals);
			o.addProperty("optimizedAnchors", optimizedAnchors);
			o.addProperty("optimizedPearlCatch", optimizedPearlCatch);
			o.addProperty("itemSpeed", itemSpeed);
			Files.writeString(file(), GSON.toJson(o));
		} catch (Exception ignored) {
		}
	}
}
