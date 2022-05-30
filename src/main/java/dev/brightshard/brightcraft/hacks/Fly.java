package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.util.math.Vec3d;

import org.lwjgl.glfw.GLFW;

import java.util.Objects;

import static dev.brightshard.brightcraft.lib.MathTools.addVectors;

public class Fly extends Hack {
    private Vec3d newVelocity = new Vec3d(0, 0, 0);

    public Fly() {
        super("Fly", "Fly", "Fly hacks\nRecommendation: Enable \"No Fall Damage\", too!", GLFW.GLFW_KEY_Z);
    }

    @Override
    public void onEnable() {
        Hack.getHackById("Jetpack").disable();
        player.flying(true);
    }
    @Override
    public void onDisable() {
        Objects.requireNonNull(Hack.getHackById("NoClip")).disable();
        player.flying(false);
    }

    @Override
    public void tick() {
        // Update FlySpeed in case it was changed
        double flySpeed;
        try {
            flySpeed = Double.parseDouble(config.getConfig("FlySpeed"));
        // Default to 1.0 if there's no configuration for it (i.e. the config file was deleted or doesn't exist)
        } catch (NumberFormatException e) {
            flySpeed = 1.0;
            config.setConfig("FlySpeed", "1.0");
        }

        // Strafe controls
        if (client.options.forwardKey.isPressed()) {
            newVelocity = addVectors(newVelocity, player.moveForwards(flySpeed));
        }
        if (client.options.backKey.isPressed()) {
            newVelocity = addVectors(newVelocity, player.moveBackwards(flySpeed));
        }
        if (client.options.leftKey.isPressed()) {
            newVelocity = addVectors(newVelocity, player.moveLeft(flySpeed));
        }
        if (client.options.rightKey.isPressed()) {
            newVelocity = addVectors(newVelocity, player.moveRight(flySpeed));
        }

        // Up/Down
        if (client.options.jumpKey.isPressed()) {
            newVelocity = addVectors(newVelocity, player.moveUp(flySpeed));
        }
        if (client.options.sneakKey.isPressed()) {
            newVelocity = addVectors(newVelocity, player.moveDown(flySpeed));
        }

        // Move the player
        player.setVelocity(newVelocity);
        newVelocity = Vec3d.ZERO;
    }
}
