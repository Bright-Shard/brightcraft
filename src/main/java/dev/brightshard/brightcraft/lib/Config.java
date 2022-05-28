package dev.brightshard.brightcraft.lib;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static dev.brightshard.brightcraft.Main.LOGGER;

public class Config {
    public Block[] visibleBlocks;
    private final Properties config = new Properties();
    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("brightcraft.properties");
    public double defaultGamma;
    public boolean loaded = false;
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
        this.visibleBlocks = new Block[]{
                // Iron
                Blocks.IRON_ORE,
                Blocks.DEEPSLATE_IRON_ORE,
                Blocks.IRON_BLOCK,
                Blocks.RAW_IRON_BLOCK,
                // Diamond
                Blocks.DIAMOND_ORE,
                Blocks.DEEPSLATE_DIAMOND_ORE,
                Blocks.DIAMOND_BLOCK,
                // Gold
                Blocks.GOLD_ORE,
                Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.NETHER_GOLD_ORE,
                Blocks.GOLD_BLOCK,
                Blocks.RAW_GOLD_BLOCK,
                // Copper
                Blocks.COPPER_ORE,
                Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.COPPER_BLOCK,
                Blocks.RAW_COPPER_BLOCK,
                // Coal
                Blocks.COAL_ORE,
                Blocks.DEEPSLATE_COAL_ORE,
                Blocks.COAL_BLOCK,
                // Netherite
                Blocks.ANCIENT_DEBRIS,
                Blocks.NETHERITE_BLOCK,
                // Amethyst
                Blocks.AMETHYST_BLOCK,
                Blocks.AMETHYST_CLUSTER,
                Blocks.BUDDING_AMETHYST,
                Blocks.SMALL_AMETHYST_BUD,
                Blocks.MEDIUM_AMETHYST_BUD,
                Blocks.LARGE_AMETHYST_BUD,
                // Chests
                Blocks.CHEST,
                Blocks.ENDER_CHEST,
                // End
                Blocks.END_PORTAL,
                Blocks.END_PORTAL_FRAME,
                Blocks.END_GATEWAY
        };
        this.defaultGamma = PlayerManager.getInstance().getClient().options.gamma;
        this.loaded = true;
        LOGGER.info("Finished loading BrightCraft config");
    }
}
