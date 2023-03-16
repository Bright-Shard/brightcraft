package dev.brightshard.brightcraft.lib.Config;

import static dev.brightshard.brightcraft.BrightCraft.LOGGER;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Properties;

public class Config {
    public interface SerializedConfig {
        void setValue(String value);
        String getValue();
    }

    private final Properties configProperties = new Properties();
    private static final Path configFile = FabricLoader
                                            .getInstance()
                                            .getConfigDir()
                                            .resolve("brightcraft.properties");

    private final HashMap<String, SerializedConfig> config = new HashMap<>();

    public Config() {
        LOGGER.info("Loading BrightCraft Config");
        this.loadConfig();
    }

    // Add a config item
    public void addConfig(String id, SerializedConfig config) {
        String propertiesValue = this.configProperties.getProperty(id);
        if (propertiesValue != null) {
            config.setValue(propertiesValue);
        }
        this.config.put(id, config);
    }
    // Set a config item
    public void setConfig(String id, String value) {
        this.config.get(id).setValue(value);
        this.configProperties.put(id, value);
    }

    // Save/load configuration file
    public void saveConfig() {
        try {
            this.configProperties.store(Files.newOutputStream(configFile), "BrightCraft configuration");
        } catch (IOException e) {
            LOGGER.error("Error saving configuration file: ", e);
        }
    }
    public void loadConfig() {
        try {
            this.configProperties.load(Files.newInputStream(configFile));
        } catch (IOException e) {
            LOGGER.error("Error loading configuration file: ", e);
        }
    }
}

/*
public class Config {
    private final Properties config = new Properties();
    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("brightcraft.properties");
    private static final Config instance = new Config();
    public Config() {
        LOGGER.info("Loading BrightCraft Config");
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
    public String getConfig(String id, String defaultValue) {
        if (!this.config.containsKey(id)) {
            this.setConfig(id, defaultValue);
        }
        return this.config.getProperty(id);
    }
    public boolean toggleHack(String id) {
        Hack hack = Hack.getHackById(id);
        if (hack.getClass() == Hack.EmptyHackClass.class) {
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
}
*/
