package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Event.EventDataBuffer;
import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Hack;
import dev.brightshard.brightcraft.lib.XRayBlockGroup;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.lwjgl.glfw.GLFW;

import static dev.brightshard.brightcraft.BrightCraft.*;

public class XRay extends Hack {
    public XRay() {
        super(
                HackType.XRay,
                "XRay",
                "Only show important blocks (ores, monsters, lava, etc)\nAlso enables Fullbright",
                GLFW.GLFW_KEY_X
        );
        this.listen(Events.BlockShouldRender, this::blockShouldRender);
    }

    // Handles the BlockShouldRender event
    private Boolean blockShouldRender(Block data) {
        for (XRayBlockGroup group : BLOCKS) {
            if (group.enabled() && group.blocks.contains(data)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    // The world renderer needs to be reloaded when the hack is enabled/disabled
    @Override
    public void enable() {
        super.enable();
        CLIENT.worldRenderer.reload();
    }
    @Override
    public void disable() {
        super.disable();
        CLIENT.worldRenderer.reload();
    }

    public static final XRayBlockGroup[] BLOCKS = {
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
                    // Quarrz
                    Blocks.NETHER_QUARTZ_ORE,
            }),
            new XRayBlockGroup("Structures", "Blocks useful for finding certain biomes and structures", new Block[]{
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
                    // idek anymore
                    Blocks.HOPPER,
            })
    };
}
