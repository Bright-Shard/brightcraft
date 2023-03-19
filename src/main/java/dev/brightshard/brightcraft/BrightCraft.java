package dev.brightshard.brightcraft;

import dev.brightshard.brightcraft.lib.*;
import dev.brightshard.brightcraft.lib.Config.Config;
import dev.brightshard.brightcraft.lib.Event.*;
import dev.brightshard.brightcraft.patches.*;
import dev.brightshard.brightcraft.hacks.*;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrightCraft implements ClientModInitializer {
    // Constants for the whole client
    public static final Logger LOGGER = LoggerFactory.getLogger("BrightCraft");
    public static final MinecraftClient RAWCLIENT = MinecraftClient.getInstance();
    public static final PatchedClient CLIENT = (PatchedClient) RAWCLIENT;
    public static final PatchedPlayer PLAYER = new PatchedPlayer();
    public static boolean INITIALIZED = false;
    public static boolean ENABLED = false;
    public static final KeybindManager KEYBINDS = new KeybindManager();
    public static final Config CONFIG = new Config();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Starting BrightCraft...");

        // Don't init twice
        if (INITIALIZED) {
            throw new RuntimeException("BrightCraft started twice!");
        }

        LOGGER.info("|- Binding events");
        ClientTickEvents.START_CLIENT_TICK.register(this::preTick);
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
        ClientPlayConnectionEvents.JOIN.register(this::worldJoined);
        ClientPlayConnectionEvents.DISCONNECT.register(this::worldLeft);
        ClientLifecycleEvents.CLIENT_STOPPING.register(this::onExit);

        LOGGER.info("|- Start hacks");
        new Antikick();
        new Fly();
        new Fullbright();
        new Instabreak();
        new Jetpack();
        new NoClip();
        new NoFallDamage();
        new Speed();
        new XRay();

        INITIALIZED = true;
        LOGGER.info("BrightCraft started!");
    }

    // Fires the Tick event
    private void tick(MinecraftClient client) {
        if (ENABLED) {
            try (LockedBuffer<SimpleEvent>.Lock lock = Events.Tick.lock()) {
                lock.readBuffer().fire();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Fires the PreTick event
    private void preTick(MinecraftClient client) {
        if (ENABLED) {
            try (LockedBuffer<SimpleEvent>.Lock lock = Events.PreTick.lock()) {
                lock.readBuffer().fire();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Fires the WorldJoined event
    private void worldJoined(ClientPlayNetworkHandler clientPlayNetworkHandler, PacketSender packetSender, MinecraftClient client) {
        LOGGER.info("World joined!");
        ENABLED = true;
        try (LockedBuffer<SimpleEvent>.Lock lock = Events.WorldJoined.lock()) {
            lock.readBuffer().fire();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Fires the WorldLeft event
    private void worldLeft(ClientPlayNetworkHandler clientPlayNetworkHandler, MinecraftClient client) {
        LOGGER.info("World left!");
        ENABLED = false;
        try (LockedBuffer<SimpleEvent>.Lock lock = Events.WorldLeft.lock()) {
            lock.readBuffer().fire();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Saves config when Minecraft exits
    private void onExit(MinecraftClient client) {
        CONFIG.saveConfig();
    }
}