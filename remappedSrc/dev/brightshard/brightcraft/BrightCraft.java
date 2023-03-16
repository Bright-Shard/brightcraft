package dev.brightshard.brightcraft;

import dev.brightshard.brightcraft.hacks.*;
import dev.brightshard.brightcraft.managers.ClientManager;
import dev.brightshard.brightcraft.lib.*;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrightCraft implements ClientModInitializer {
    // Constants for the whole client
    public static final Logger LOGGER = LoggerFactory.getLogger("BrightCraft");
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static final ClientManager CLIENT = (ClientManager) MC;
    public static BrightCraft BRIGHTCRAFT;
    public static boolean INITIALIZED = false;
    public static boolean ENABLED = false;
    public static final KeybindManager KEYBINDS = new KeybindManager();
    public static final EventManager EVENTS = new EventManager();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Starting BrightCraft...");

        // Don't init twice
        if (INITIALIZED) {
            throw new RuntimeException("BrightCraft started twice!");
        }

        // Start the hacks
        LOGGER.info("|- Starting hacks");
        new Jetpack();
        new Fullbright();
        new NoFallDamage();
        new Instabreak();
        new XRay();
        new NoClip();
        new Speed();

        BRIGHTCRAFT = this;
        INITIALIZED = true;
        LOGGER.info("BrightCraft started!");
    }
}
