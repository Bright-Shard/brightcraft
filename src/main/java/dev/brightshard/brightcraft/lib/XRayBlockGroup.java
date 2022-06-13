package dev.brightshard.brightcraft.lib;

import net.minecraft.block.Block;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class XRayBlockGroup {
    public final List<Block> blocks;
    public final String name;
    public final String tooltip;

    public XRayBlockGroup(String name, String tooltip, Block[] blocks) {
        this.name = name;
        this.tooltip = tooltip;
        this.blocks = Arrays.asList(blocks);
    }

    public boolean enabled() {
        return Objects.equals(Config.getInstance().getConfig("XRay."+this.name), "true");
    }
}
