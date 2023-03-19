package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Hack;

import net.minecraft.util.math.Vec3d;

import org.lwjgl.glfw.GLFW;

import static dev.brightshard.brightcraft.BrightCraft.*;

public class Jetpack extends Hack {
    public Jetpack() {
        super(
                HackType.Jetpack,
                "Jetpack",
                "Haha jetpack go brrrrr\nRecommendation: Enable \"No Fall Damage\", too!",
                GLFW.GLFW_KEY_J
        );
        this.listen(Events.Tick, this::tick);
        this.addBlacklist(HackType.Fly);
        this.addDependency(HackType.Antikick);
    }

    private void tick() {
        // Reset newVelocity
        assert RAWCLIENT.player != null;
        Vec3d velocity = RAWCLIENT.player.getVelocity();
        // Update FlySpeed in case it was changed
        //double flySpeed = Double.parseDouble(CONFIG.getConfig("FlySpeed", "1.0"));
        double flySpeed = 1.0;

        // Better air strafing
        RAWCLIENT.player.airStrafingSpeed = (float) flySpeed / 12;

        // Jump for jetpack
        if (CLIENT.getOptions().jumpKey.isPressed()) {
            PLAYER.velocity = new Vec3d(velocity.x, flySpeed, velocity.z);
        }
    }
}
