package dev.brightshard.brightcraft.lib;

import dev.brightshard.brightcraft.lib.Config.ConfigItem;
import net.minecraft.block.Block;

import java.util.Arrays;
import java.util.List;

public class XRayBlockGroup {
    public final List<Block> blocks;
    public final String name;
    public final String tooltip;
    public final ConfigItem<Boolean> enabled;

    public XRayBlockGroup(String name, String tooltip, Block[] blocks) {
        this.name = name;
        this.tooltip = tooltip;
        this.blocks = Arrays.asList(blocks);
        this.enabled = new ConfigItem<>("XrayGroup." + name, false);
    }

    public boolean enabled() {
        return this.enabled.getRawValue();
    }
    public void setEnabled(boolean value) {
        this.enabled.setRawValue(value);
    }
}
