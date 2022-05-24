package dev.brightshard.brightcraft;

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
	// For antikick
	private static int tickCounter = 40;
	private static final double fallDistance = 0.04;

	// Useful services
	public static final Logger LOGGER = LoggerFactory.getLogger("BrightCraft");
	private final PlayerManager playerManager = PlayerManager.getInstance();
	private final KeyBindManager keyBindManager = KeyBindManager.getInstance();
	private boolean worldJoined = false;

	// Only have one instance running
	private static Main instance;

	@Override
	public void onInitializeClient() {
		if (instance == null) {
			LOGGER.info("Starting BrightCraft...");
			instance = this;
			ClientTickEvents.END_CLIENT_TICK.register(this::tick);
			ClientLifecycleEvents.CLIENT_STOPPING.register(this::onExitClient);
			LOGGER.info("Initializing BrightCraft hacks...");
			InitHacks.initHacks();
			LOGGER.info("Registering BrightCraft keybinds...");
			keyBindManager.registerKeyBinds();
			LOGGER.info("Started BrightCraft.");
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
		playerManager.tick(client);
		keyBindManager.tick();

		if (playerManager.ready()) {
			if (!Config.getInstance().loaded) {
				Config.getInstance().load();
			}

			if (worldJoined) {
				this.onWorldJoin();
			}

			for (Hack hack : Hack.getHacks()) {
				if (hack.enabled()) {
					hack.tick();
				}
			}

			antikick();
		}
	}

	private void antikick() {
		// Only run when the player is flying
		if (!playerManager.flying()) {
			tickCounter = 40;
			return;
		}

		// Player velocity
		Vec3d velocity = playerManager.getVelocity();

		// 1 tick after the counter hits 0, move the player back up and reset the flyCounter
		if (tickCounter == 0) {
			playerManager.overrideVelocity(new Vec3d(velocity.x, fallDistance, velocity.z));
			tickCounter = 40;
		}

		// Decrement the flyCounter once per tick
		--tickCounter;

		// When the counter hits 0, move the player down
		if (tickCounter == 0) {
			playerManager.overrideVelocity(new Vec3d(velocity.x, -fallDistance, velocity.z));
			LOGGER.info("Antikick ran");
		}
	}

	public void onWorldJoin() {
		if (!playerManager.ready()) {
			this.worldJoined = true;
			return;
		}
		Chat chat = new Chat();
		chat.addToMessage(Chat.divider);
		chat.addToMessage("\n"+Chat.label+"Current hack statuses:\n");
		for (Hack hack : Hack.getHacks()) {
			chat.addToMessage(hack.name+": ");
			if (hack.enabled()) {
				chat.addToMessage(Chat.on);
			} else {
				chat.addToMessage(Chat.off);
			}
			chat.addToMessage("\n");
		}
		chat.addToMessage(Chat.divider);
		chat.sendLongMessage();
		this.worldJoined = false;
	}

	public static Main getInstance() {
		return instance;
	}
}
