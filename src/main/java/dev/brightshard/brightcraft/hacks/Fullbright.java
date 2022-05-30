package dev.brightshard.brightcraft.hacks;

import org.lwjgl.glfw.GLFW;

import dev.brightshard.brightcraft.lib.Hack;

// Thx Wurst Client
public class Fullbright extends Hack {
    public Fullbright() {
        super("FullBright", "Full Bright", "Like nightvision, but better", GLFW.GLFW_KEY_B);
    }

    @Override
    public void onEnable() {
        eventManager.bindEvent("AmbientOcclusion", this.id, (cir, data) -> cir.setReturnValue(false));
        client.worldRenderer.reload();
    }
    @Override
    public void onDisable() {
        eventManager.releaseEvent("AmbientOcclusion", this.id);
        client.options.gamma = config.defaultGamma;
        client.worldRenderer.reload();
    }
    @Override
    public void tick() {
        client.options.gamma = 16;
    }
}
