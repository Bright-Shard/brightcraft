package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventHandler;
import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import static dev.brightshard.brightcraft.lib.MathTools.addVectors;

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
        double speed = Double.parseDouble(config.getConfig("SpeedSpeed", "1.0"));
        // Reset player velocity
        Vec3d newVelocity = Vec3d.ZERO;

        // Strafe controls
        if (client.options.forwardKey.isPressed()) {
            newVelocity = addVectors(newVelocity, player.moveForwardsFlat(speed));
        }
        if (client.options.backKey.isPressed()) {
            newVelocity = addVectors(newVelocity, player.moveBackwardsFlat(speed));
        }
        if (client.options.leftKey.isPressed()) {
            newVelocity = addVectors(newVelocity, player.moveLeft(speed));
        }
        if (client.options.rightKey.isPressed()) {
            newVelocity = addVectors(newVelocity, player.moveRight(speed));
        }

        // Move the player
        if (newVelocity != Vec3d.ZERO) player.setVelocity(new Vec3d(newVelocity.x, player.getVelocity().y, newVelocity.z));
    }
}
