package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.util.math.Vec3d;

import org.lwjgl.glfw.GLFW;

import static dev.brightshard.brightcraft.lib.MathTools.addVectors;

public class Jetpack extends Hack {

    public Jetpack() {
        super("Jetpack", "Jetpack", "Haha jetpack go brrrrr\nRecommendation: Enable \"No Fall Damage\", too!", GLFW.GLFW_KEY_J);
    }

    @Override
    public void onEnable() {
        Hack.getHackById("Fly").disable();
        player.flying(true);
    }
    @Override
    public void onDisable() {
        player.flying(false);
    }

    @Override
    public void tick() {
        // Reset newVelocity
        Vec3d velocity = player.getVelocity();
        // Update FlySpeed in case it was changed
        double flySpeed;
        try {
            flySpeed = Double.parseDouble(config.getConfig("FlySpeed"));
            // Default to 1.0 if there's no configuration for it (i.e. the config file was deleted or doesn't exist)
        } catch (NumberFormatException e) {
            flySpeed = 1.0;
            config.setConfig("FlySpeed", "1.0");
        }

        // Better air strafing
        player.getPlayerEntity().airStrafingSpeed = (float) flySpeed / 10;

        // Jump for jetpack
        if (client.options.jumpKey.isPressed()) {
            player.setVelocity(new Vec3d(velocity.x, flySpeed, velocity.z));
        }
    }
}
