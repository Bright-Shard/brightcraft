package dev.brightshard.brightcraft.lib;

import dev.brightshard.brightcraft.managers.PlayerManager;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static dev.brightshard.brightcraft.Main.LOGGER;

public class Config {
    private final Properties config = new Properties();
    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("brightcraft.properties");
    public double defaultGamma;
    private static final Config instance = new Config();

    public Config() {
        LOGGER.info("Loading BrightCraft config");
        try {
            config.load(Files.newInputStream(configFile));
        } catch (IOException e) {
            LOGGER.warn("Error loading configuration file - default values will be used.");
        }
    }

    // Get Configuration instance
    public static Config getInstance() {
        return instance;
    }

    // Save/load configuration file
    public void saveConfig() {
        try {
            this.config.store(Files.newOutputStream(configFile), "BrightCraft configuration");
        } catch (IOException e) {
            LOGGER.error("Error saving configuration file: ", e);
        }
    }

    // Get or change settings
    public void setConfig(String id, String value) {
        this.config.setProperty(id, value);
        this.saveConfig();
    }
    public String getConfig(String id) {
        if (!this.config.containsKey(id)) {
            this.setConfig(id, "false");
        }
        return this.config.getProperty(id);
    }
    public boolean toggleHack(String id) {
        Hack hack = Hack.getHackById(id);
        if (hack == null) {
            return false;
        }
        boolean wasEnabled = hack.enabled();

        this.config.setProperty(id, this.config.getProperty(id).equals("true") ? "false" : "true");
        boolean enabled = this.config.getProperty(id).equals("true");

        if (enabled) {
            hack.onEnable();
            if (!wasEnabled) {
                new Chat(hack);
            }
        } else {
            hack.onDisable();
            if (wasEnabled) {
                new Chat(hack);
            }
        }
        return enabled;
    }

    public void load() {
        this.defaultGamma = PlayerManager.getInstance().getClient().options.gamma;
    }
}
