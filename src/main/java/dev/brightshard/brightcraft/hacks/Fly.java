package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Hack;

import org.lwjgl.glfw.GLFW;

import static dev.brightshard.brightcraft.BrightCraft.*;

public class Fly extends Hack {
    public Fly() {
        super(
                HackType.Fly,
                "Fly",
                "Fly hacks\nRecommendation: Enable \"No Fall Damage\", too!",
                GLFW.GLFW_KEY_Z
        );
        this.listen(Events.Tick, this::tick);
        this.addBlacklist(HackType.Jetpack);
        this.addBlacklist(HackType.Speed);
        this.addDependency(HackType.Antikick);
    }

    private void tick() {
        // Update FlySpeed in case it was changed
        //double flySpeed = Double.parseDouble(CONFIG.getConfig("FlySpeed", "1.0"));
        double flySpeed = 1.0;

        // Strafe controls
        if (CLIENT.getOptions().forwardKey.isPressed()) {
            PLAYER.moveForwards(flySpeed);
        }
        if (CLIENT.getOptions().backKey.isPressed()) {
            PLAYER.moveBackwards(flySpeed);
        }
        if (CLIENT.getOptions().leftKey.isPressed()) {
            PLAYER.moveLeft(flySpeed);
        }
        if (CLIENT.getOptions().rightKey.isPressed()) {
            PLAYER.moveRight(flySpeed);
        }

        // Up/Down
        if (CLIENT.getOptions().jumpKey.isPressed()) {
            PLAYER.moveUp(flySpeed);
        }
        if (CLIENT.getOptions().sneakKey.isPressed()) {
            PLAYER.moveDown(flySpeed);
        }
    }
}
