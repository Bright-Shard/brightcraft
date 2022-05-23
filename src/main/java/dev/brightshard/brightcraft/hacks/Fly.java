package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.util.math.Vec3d;

import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class Fly extends Hack {
    private int doubleSpaceTimer = -1;
    private Vec3d newVelocity = new Vec3d(0, 0, 0);

    public Fly() {
        super("Fly", "Fly", "Fly hacks\nRecommendation: Enable \"No Fall Damage\", too!", GLFW.GLFW_KEY_Z);
    }

    @Override
    public void onEnable() {

    }
    @Override
    public void onDisable() {
        Objects.requireNonNull(Hack.getHackById("NoClip")).disable();
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

        // Enable flight if space bar is hit twice before the time runs out
        if (doubleSpaceTimer >= 0 && doubleSpaceTimer < 4 && client.options.jumpKey.isPressed()) {
            playerManager.flying(!playerManager.flying());
        }

        // If the player is flying, allow strafing controls & make them hover
        if (playerManager.flying()) {
            // Strafe controls
            //player.airStrafingSpeed = (float) flySpeed;
            if (client.options.forwardKey.isPressed()) {
                newVelocity = addVec(newVelocity, playerManager.moveForwards(flySpeed));
            }
            if (client.options.backKey.isPressed()) {
                newVelocity = addVec(newVelocity, playerManager.moveBackwards(flySpeed));
            }
            if (client.options.leftKey.isPressed()) {
                newVelocity = addVec(newVelocity, playerManager.moveLeft(flySpeed));
            }
            if (client.options.rightKey.isPressed()) {
                newVelocity = addVec(newVelocity, playerManager.moveRight(flySpeed));
            }

            // Up/Down
            if (client.options.jumpKey.isPressed()) {
                newVelocity = addVec(newVelocity, playerManager.moveUp(flySpeed));
            }
            if (client.options.sneakKey.isPressed()) {
                newVelocity = addVec(newVelocity, playerManager.moveDown(flySpeed));
            }

            // Move the player
            playerManager.setVelocity(newVelocity);
            newVelocity = new Vec3d(0, 0, 0);
        }

        // Start the timer for the second space bar hit if it was hit once
        if (client.options.jumpKey.isPressed()) {
            doubleSpaceTimer = 5;
        }

        if (doubleSpaceTimer > -1) {
            --doubleSpaceTimer;
        }
    }

    private Vec3d addVec(Vec3d one, Vec3d two) {
        return new Vec3d(one.x + two.x, one.y + two.y, one.z + two.z);
    }
}
