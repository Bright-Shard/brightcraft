package dev.brightshard.brightcraft.hacks;

import org.lwjgl.glfw.GLFW;

import dev.brightshard.brightcraft.lib.Hack;

// Thx Wurst Client
public class Fullbright extends Hack {
    private double clientGamma;
    public Fullbright() {
        super("FullBright", "Full Bright", "Like nightvision, but better", GLFW.GLFW_KEY_B);
    }

    @Override
    public void onEnable() {
        client.worldRenderer.reload();
    }
    @Override
    public void onDisable() {
        client.options.gamma = config.defaultGamma;
        client.worldRenderer.reload();
    }
    @Override
    public void tick() {
        client.options.gamma = 16;
    }
}
