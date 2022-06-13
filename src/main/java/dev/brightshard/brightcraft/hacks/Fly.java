package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventHandler;
import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.util.math.Vec3d;

import org.lwjgl.glfw.GLFW;

import java.util.Objects;

import static dev.brightshard.brightcraft.lib.MathTools.addVectors;

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
        super.onEnable();
        player.flying(true);
    }

    @Override
    public void onDisable() {
        Objects.requireNonNull(Hack.getHackById("NoClip")).disable();
        super.onDisable();
        player.flying(false);
    }

    private void tick() {
        // Update FlySpeed in case it was changed
        double flySpeed = Double.parseDouble(config.getConfig("FlySpeed", "1.0"));

        // Strafe controls
        if (client.options.forwardKey.isPressed()) {
            player.moveForwards(flySpeed);
        }
        if (client.options.backKey.isPressed()) {
            player.moveBackwards(flySpeed);
        }
        if (client.options.leftKey.isPressed()) {
            player.moveLeft(flySpeed);
        }
        if (client.options.rightKey.isPressed()) {
            player.moveRight(flySpeed);
        }

        // Up/Down
        if (client.options.jumpKey.isPressed()) {
            player.moveUp(flySpeed);
        }
        if (client.options.sneakKey.isPressed()) {
            player.moveDown(flySpeed);
        }
    }
}
