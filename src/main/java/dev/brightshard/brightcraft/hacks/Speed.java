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
        double speed = Double.parseDouble(config.getConfig("SpeedAmount", "1.0"));

        // Strafe controls
        if (client.options.forwardKey.isPressed()) {
            player.moveForwardsFlat(speed);
        }
        if (client.options.backKey.isPressed()) {
            player.moveBackwardsFlat(speed);
        }
        if (client.options.leftKey.isPressed()) {
            player.moveLeft(speed);
        }
        if (client.options.rightKey.isPressed()) {
            player.moveRight(speed);
        }
    }
}
