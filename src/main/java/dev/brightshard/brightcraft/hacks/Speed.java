package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventHandler;
import dev.brightshard.brightcraft.lib.Hack;
import org.lwjgl.glfw.GLFW;

public class Speed extends Hack {
    public Speed() {
        super("Speed", "Speed", "Move faster", GLFW.GLFW_KEY_V);
        Speed instance = this;
        this.handlers.add(new EventHandler(instance.id, "tick") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                instance.tick();
            }
        });
    }

    private void tick() {
        // Update FlySpeed in case it was changed
        double speed = Double.parseDouble(CONFIG.getConfig("SpeedAmount", "1.0"));

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
