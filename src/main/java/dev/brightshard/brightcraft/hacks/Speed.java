package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Hack;

import static dev.brightshard.brightcraft.BrightCraft.*;

import org.lwjgl.glfw.GLFW;

public class Speed extends Hack {
    public Speed() {
        super(
                HackType.Speed,
                "Speed",
                "Move faster",
                GLFW.GLFW_KEY_V
        );
        this.listen(Events.Tick, this::tick);
        this.addBlacklist(HackType.Fly);
    }

    private void tick() {
        // Update FlySpeed in case it was changed
        //double speed = Double.parseDouble(CONFIG.getConfig("SpeedAmount", "1.0"));
        double speed = 1.0;

        // Strafe controls
        if (CLIENT.getOptions().forwardKey.isPressed()) {
            PLAYER.moveForwardsFlat(speed);
        }
        if (CLIENT.getOptions().backKey.isPressed()) {
            PLAYER.moveBackwardsFlat(speed);
        }
        if (CLIENT.getOptions().leftKey.isPressed()) {
            PLAYER.moveLeftFlat(speed);
        }
        if (CLIENT.getOptions().rightKey.isPressed()) {
            PLAYER.moveRightFlat(speed);
        }
    }
}
