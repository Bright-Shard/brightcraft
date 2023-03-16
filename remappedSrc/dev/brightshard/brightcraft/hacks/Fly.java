package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.EventData;
import dev.brightshard.brightcraft.lib.EventHandler;
import dev.brightshard.brightcraft.lib.Antikick;
import dev.brightshard.brightcraft.lib.Hack;

import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class Fly extends Hack {

    public Fly() {
        super("Fly", "Fly", "Fly hacks\nRecommendation: Enable \"No Fall Damage\", too!", GLFW.GLFW_KEY_Z);
        Fly instance = this;
        this.handlers.add(new EventHandler(instance.id, "tick") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                instance.tick();
            }
        });
    }

    @Override
    public void onEnable() {
        Hack.getHackById("Jetpack").disable();
        Antikick.enable();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Objects.requireNonNull(Hack.getHackById("NoClip")).disable();
        super.onDisable();
        Antikick.disable();
    }

    private void tick() {
        // Update FlySpeed in case it was changed
        double flySpeed = Double.parseDouble(CONFIG.getConfig("FlySpeed", "1.0"));

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
