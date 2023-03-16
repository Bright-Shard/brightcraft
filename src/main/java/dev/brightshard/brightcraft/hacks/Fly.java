package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Hack;

import static dev.brightshard.brightcraft.BrightCraft.CLIENT;

import org.lwjgl.glfw.GLFW;

public class Fly extends Hack {
    public Fly() {
        super(
                HackType.Fly,
                "Fly",
                "Fly hacks\nRecommendation: Enable \"No Fall Damage\", too!",
                GLFW.GLFW_KEY_Z
        );
        this.bindEvent(Events.Tick, this::tick);
        this.addBlacklist(HackType.Jetpack);
        this.addDependency(HackType.Antikick);
    }

    private void tick() {
        // Update FlySpeed in case it was changed
        //double flySpeed = Double.parseDouble(CONFIG.getConfig("FlySpeed", "1.0"));
        double flySpeed = 1.0;

        // Strafe controls
        if (CLIENT.getOptions().forwardKey.isPressed()) {
            CLIENT.getPlayer().moveForwards(flySpeed);
        }
        if (CLIENT.getOptions().backKey.isPressed()) {
            CLIENT.getPlayer().moveBackwards(flySpeed);
        }
        if (CLIENT.getOptions().leftKey.isPressed()) {
            CLIENT.getPlayer().moveLeft(flySpeed);
        }
        if (CLIENT.getOptions().rightKey.isPressed()) {
            CLIENT.getPlayer().moveRight(flySpeed);
        }

        // Up/Down
        if (CLIENT.getOptions().jumpKey.isPressed()) {
            CLIENT.getPlayer().moveUp(flySpeed);
        }
        if (CLIENT.getOptions().sneakKey.isPressed()) {
            CLIENT.getPlayer().moveDown(flySpeed);
        }
    }
}
