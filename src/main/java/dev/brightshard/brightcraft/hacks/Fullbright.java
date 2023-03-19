package dev.brightshard.brightcraft.hacks;

import static dev.brightshard.brightcraft.BrightCraft.CLIENT;
import static dev.brightshard.brightcraft.BrightCraft.LOGGER;

import dev.brightshard.brightcraft.lib.Event.EventDataBuffer;
import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Event.Listener;
import dev.brightshard.brightcraft.lib.Hack;

import org.lwjgl.glfw.GLFW;

// Thx Wurst Client
public class Fullbright extends Hack {
    private double originalGamma;
    private double gammaTweenAmount;
    private final Listener gammaTweenEvent;

    public Fullbright() {
        super(
                HackType.Fullbright,
                "Full Bright",
                "Like night vision, but better",
                GLFW.GLFW_KEY_B
        );

        // Tween gamma values when the hack is enabled/disabled
        this.gammaTweenEvent = this.listen(Events.Tick, this::tweenGamma);
        this.gammaTweenEvent.bound = false;

        // Disable ambient occlusion / shading
        this.listen(Events.DisableAmbientOcclusion, this::disableAmbientOcclusion);

        // Improve visibility underwater
        this.listen(Events.MaxUnderwaterVisibility, this::maxUnderwaterVisibility);
    }

    // Disables Ambient Occlusion (shading) when FullBright is on
    private Boolean disableAmbientOcclusion() {
        return Boolean.TRUE;
    }

    // Maximise underwater visibility when FullBright is on
    private Boolean maxUnderwaterVisibility() {
        return Boolean.TRUE;
    }

    // Smoothly change the gamma values on the client
    private void tweenGamma() {
        double currentGamma = CLIENT.getOptionsPatched().getGamma();
        if (currentGamma <= 16 && currentGamma >= this.originalGamma) {
            CLIENT.getOptionsPatched().setGamma(currentGamma + this.gammaTweenAmount);
        } else {
            this.gammaTweenEvent.bound = false;
        }
    }

    @Override
    public void enable() {
        // Store what the gamma value was before enabling FullBright, so we can reset it later
        this.originalGamma = CLIENT.getOptionsPatched().getGamma();

        // Tween gamma to max
        this.gammaTweenAmount = 0.5;
        this.gammaTweenEvent.bound = true;

        super.enable();
    }

    @Override
    public void disable() {
        super.disable();

        // Tween gamma to min (must run AFTER disable because otherwise the tick listener gets disabled)
        this.gammaTweenAmount = -0.5;
        this.gammaTweenEvent.bound = true;
    }
}
