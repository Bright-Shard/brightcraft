package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Event.EventType;
import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Hack;

import static dev.brightshard.brightcraft.BrightCraft.CLIENT;

import org.lwjgl.glfw.GLFW;

public class Speed extends Hack {
    public Speed() {
        super(
                HackType.Speed,
                "Speed",
                "Move faster",
                GLFW.GLFW_KEY_V
        );
        this.bindEvent(Events.Tick, this::tick);
    }

    private void tick() {
        // Update FlySpeed in case it was changed
        //double speed = Double.parseDouble(CONFIG.getConfig("SpeedAmount", "1.0"));
        double speed = 1.0;

        // Strafe controls
        if (CLIENT.getOptions().forwardKey.isPressed()) {
            CLIENT.getPlayer().moveForwardsFlat(speed);
        }
        if (CLIENT.getOptions().backKey.isPressed()) {
            CLIENT.getPlayer().moveBackwardsFlat(speed);
        }
        if (CLIENT.getOptions().leftKey.isPressed()) {
            CLIENT.getPlayer().moveLeft(speed);
        }
        if (CLIENT.getOptions().rightKey.isPressed()) {
            CLIENT.getPlayer().moveRight(speed);
        }
    }
}
