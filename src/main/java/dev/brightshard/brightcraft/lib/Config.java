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
    private final Properties config = new Properties();
    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("brightcraft.properties");
    private static final Config instance = new Config();
    public final XRayBlockGroup[] XRayBlocks = {
            new XRayBlockGroup("Ores", "Ore blocks", new Block[]{
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
                    // Redstone
                    Blocks.REDSTONE_ORE,
                    Blocks.DEEPSLATE_REDSTONE_ORE,
                    Blocks.REDSTONE_BLOCK,
            }),
            new XRayBlockGroup("Biomes/Structures", "Blocks useful for finding certain biomes and structures", new Block[]{
                    // Spawners
                    Blocks.CHEST,
                    Blocks.SPAWNER,
                    // Cave
                    Blocks.GLOW_LICHEN,
                    Blocks.DEEPSLATE,
                    // Jungle
                    Blocks.BAMBOO,
                    Blocks.BAMBOO_SAPLING,
                    Blocks.COCOA,
                    // Village
                    Blocks.FARMLAND,
                    Blocks.DIRT_PATH,
                    // Mountain
                    Blocks.SNOW_BLOCK,
                    // Mineshaft
                    Blocks.RAIL,
                    Blocks.OAK_FENCE,
                    Blocks.OAK_PLANKS,
                    // Stronghold
                    Blocks.END_PORTAL,
                    Blocks.END_PORTAL_FRAME,
                    // Geode
                    Blocks.AMETHYST_BLOCK,
                    Blocks.BUDDING_AMETHYST,
                    // Bastion
                    Blocks.GOLD_BLOCK,
                    Blocks.POLISHED_BLACKSTONE,
                    Blocks.GILDED_BLACKSTONE,
                    Blocks.BLACKSTONE_STAIRS,
                    // Fortress
                    Blocks.NETHER_BRICKS,
                    Blocks.NETHER_BRICK_FENCE,
                    Blocks.NETHER_BRICK_SLAB,
                    Blocks.NETHER_BRICK_STAIRS,
                    // End City
                    Blocks.PURPUR_BLOCK,
                    Blocks.PURPUR_PILLAR,
                    Blocks.PURPUR_SLAB,
                    Blocks.PURPUR_STAIRS,
                    Blocks.ENDER_CHEST,
            }),
            new XRayBlockGroup("Liquids", "Water and lava", new Block[]{
                    // Water/Lava
                    Blocks.LAVA,
                    Blocks.WATER,
            }),
            new XRayBlockGroup("Special", "Portal blocks, TNT, chests, etc.", new Block[]{
                    // IDK
                    Blocks.TNT,
                    Blocks.ANVIL,
                    Blocks.CHIPPED_ANVIL,
                    Blocks.DAMAGED_ANVIL,
                    Blocks.ENCHANTING_TABLE,
                    // Cauldrons
                    Blocks.CAULDRON,
                    Blocks.LAVA_CAULDRON,
                    Blocks.WATER_CAULDRON,
                    Blocks.POWDER_SNOW_CAULDRON,
                    // End
                    Blocks.END_PORTAL,
                    Blocks.END_PORTAL_FRAME,
                    Blocks.END_GATEWAY,
                    // Nether
                    Blocks.NETHER_PORTAL,
                    Blocks.OBSIDIAN,
                    // Chests
                    Blocks.CHEST,
                    Blocks.ENDER_CHEST,
                    Blocks.TRAPPED_CHEST,
            }),
            new XRayBlockGroup("Redstone", "Blocks in redstone circuits", new Block[]{
                    // Redstone
                    Blocks.REDSTONE_BLOCK,
                    Blocks.REDSTONE_WIRE,
                    Blocks.REDSTONE_TORCH,
                    Blocks.REPEATER,
                    Blocks.COMPARATOR,
                    // Buttons
                    Blocks.ACACIA_BUTTON,
                    Blocks.BIRCH_BUTTON,
                    Blocks.DARK_OAK_BUTTON,
                    Blocks.JUNGLE_BUTTON,
                    Blocks.OAK_BUTTON,
                    Blocks.SPRUCE_BUTTON,
                    Blocks.CRIMSON_BUTTON,
                    Blocks.WARPED_BUTTON,
                    Blocks.POLISHED_BLACKSTONE_BUTTON,
                    Blocks.STONE_BUTTON,
                    // Pressure plates
                    Blocks.ACACIA_PRESSURE_PLATE,
                    Blocks.BIRCH_PRESSURE_PLATE,
                    Blocks.DARK_OAK_PRESSURE_PLATE,
                    Blocks.JUNGLE_PRESSURE_PLATE,
                    Blocks.OAK_PRESSURE_PLATE,
                    Blocks.SPRUCE_PRESSURE_PLATE,
                    Blocks.CRIMSON_PRESSURE_PLATE,
                    Blocks.WARPED_PRESSURE_PLATE,
                    Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE,
                    Blocks.STONE_PRESSURE_PLATE,
                    Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
                    Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
                    // Other activation blocks
                    Blocks.LEVER,
                    Blocks.TARGET,
                    Blocks.LECTERN,
                    Blocks.OBSERVER,
                    // Dispensers/droppers
                    Blocks.DISPENSER,
                    Blocks.DROPPER,
                    // Pistons
                    Blocks.PISTON,
                    Blocks.MOVING_PISTON,
                    Blocks.PISTON_HEAD,
                    Blocks.STICKY_PISTON,
                    // Slime/honey
                    Blocks.SLIME_BLOCK,
                    Blocks.HONEY_BLOCK,
            })
    };

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
    public String getConfig(String id, String defaultValue) {
        if (!this.config.containsKey(id)) {
            this.setConfig(id, defaultValue);
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
}
