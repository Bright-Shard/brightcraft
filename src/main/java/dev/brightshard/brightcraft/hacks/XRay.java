package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;
import org.lwjgl.glfw.GLFW;

public class XRay extends Hack {
    private boolean fullBrightWasOn;
    private Hack fullbright;
    private boolean delayedEnable = false;

    public XRay() {
        super("XRay", "XRay", "Only show important blocks (ores, monsters, lava, etc)\nAlso enables Fullbright", GLFW.GLFW_KEY_X);
    }

    @Override
    public void onEnable() {
        this.fullbright = Hack.getHackById("FullBright");
        if (this.fullbright == null) {
            LOGGER.warn("this.fullbright is null");
            config.setConfig(this.id, "false");
            this.delayedEnable = true;
        } else {
            if (!this.enabled()) {
                config.setConfig(this.id, "true");
            }
            fullBrightWasOn = this.fullbright.enabled();
            if (!fullBrightWasOn) {
                fullbright.enable();
            }
            client.worldRenderer.reload();
        }
    }
    @Override
    public void onDisable() {
        if (!fullBrightWasOn) {
            this.fullbright.disable();
        }
        client.worldRenderer.reload();
    }
    @Override
    public void tick() {
        this.fullbright = Hack.getHackById("FullBright");
        if (this.delayedEnable) {
            this.delayedEnable = false;
            this.onEnable();
        }
    }
}
