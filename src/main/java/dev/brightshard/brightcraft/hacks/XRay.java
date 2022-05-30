package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public class XRay extends Hack {
    private boolean fullBrightWasOn;
    private final Hack fullbright = Hack.getHackById("FullBright");
    private final Block[] visibleBlocks = new Block[] {
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

    public XRay() {
        super("XRay", "XRay", "Only show important blocks (ores, monsters, lava, etc)\nAlso enables Fullbright", GLFW.GLFW_KEY_X);
    }

    @Override
    public void onEnable() {
        fullBrightWasOn = this.fullbright.enabled();
        this.fullbright.enable();

        client.worldRenderer.reload();

        eventManager.bindEvent("BlockShouldRender", this.id, (cir, data) -> {
            if (Arrays.asList(this.visibleBlocks).contains((Block) data)) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
        });
    }
    @Override
    public void onDisable() {
        if (!fullBrightWasOn) {
            this.fullbright.disable();
        }

        client.worldRenderer.reload();

        eventManager.releaseEvent("BlockShouldRender", this.id);
    }
    @Override
    public void tick() {
    }
}
