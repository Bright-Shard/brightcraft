package dev.brightshard.brightcraft;

import dev.brightshard.brightcraft.hacks.*;
import dev.brightshard.brightcraft.events.EventManager;
import dev.brightshard.brightcraft.managers.ClientManager;
import dev.brightshard.brightcraft.lib.Keybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.brightshard.brightcraft.lib.*;

import java.util.Objects;

public class Main implements ClientModInitializer {
	// Constants
	public static final Logger LOGGER = LoggerFactory.getLogger("BrightCraft");
	public static final MinecraftClient MC = MinecraftClient.getInstance();
	public static final ClientManager CLIENT = (ClientManager) MC;

	// Other services
	private final Keybinds keybinds = Keybinds.getInstance();
	private final Chat chat = new Chat();
	public boolean worldJoined;
	private boolean ready;

	// Only have one instance running
	private static Main instance;

	@Override
	public void onInitializeClient() {
		if (instance == null) {
			LOGGER.info("Starting BrightCraft...");
			instance = this;
			ClientTickEvents.END_CLIENT_TICK.register(this::tick);
			ClientLifecycleEvents.CLIENT_STOPPING.register(this::onExitClient);
			LOGGER.info("Instantiating hacks...");
			new Fly();
			new Jetpack();
			new Fullbright();
			new NoFallDamage();
			new Instabreak();
			new XRay();
			new NoClip();
			new Speed();
			LOGGER.info("Registering BrightCraft keybinds...");
			keybinds.registerKeyBinds();
		} else {
			throw new RuntimeException("BrightCraft initialized twice");
		}
	}

	private void onExitClient(MinecraftClient client) {
		Objects.requireNonNull(Hack.getHackById("XRay")).disable();
		Objects.requireNonNull(Hack.getHackById("FullBright")).disable();
		Config.getInstance().saveConfig();
	}

	public void tick(MinecraftClient client) {
		EventManager.fireEvent("pretick");
		if (this.worldJoined) {
			this.onWorldJoin(client);
		} else if (!this.ready) return;

		EventManager.fireEvent("tick");
		keybinds.tick();
		CLIENT.getPlayer().movePlayer();
	}

	public void onWorldJoin(MinecraftClient client) {
		if (client.player == null) { // Wait for the player to load in
			this.worldJoined = true;
			return;
		}

		LOGGER.info("World joined");

		Hack.enableHacks();

		// Chat hack statuses
		chat.clear();
		chat.addToMessage(Chat.divider + "\n" + Chat.label + "Current hack statuses:\n");
		for (Hack hack : Hack.getHacks()) {
			chat.addToMessage(hack.name + ": " + (hack.enabled() ? Chat.on : Chat.off) + "\n");
		}
		chat.addToMessage(Chat.divider);
		chat.send();

		// Show that BrightCraft is ready
		this.worldJoined = false;
		this.ready = true;
	}
	public void onWorldLeave() {
		LOGGER.info("World left");
		Hack.disableHacks();
		this.ready = false;
	}

	public boolean ready() {
		return this.ready;
	}

	public static Main getInstance() {
		return instance;
	}
}
