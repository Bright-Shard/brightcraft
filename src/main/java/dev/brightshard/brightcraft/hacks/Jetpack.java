package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Event.EventType;
import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Hack;

import static dev.brightshard.brightcraft.BrightCraft.CLIENT;

import net.minecraft.util.math.Vec3d;

import org.lwjgl.glfw.GLFW;

public class Jetpack extends Hack {
    public Jetpack() {
        super(
                HackType.Jetpack,
                "Jetpack",
                "Haha jetpack go brrrrr\nRecommendation: Enable \"No Fall Damage\", too!",
                GLFW.GLFW_KEY_J
        );
        this.bindEvent(Events.Tick, this::tick);
        this.addBlacklist(HackType.Fly);
        this.addDependency(HackType.Antikick);
    }

    private void tick() {
        // Reset newVelocity
        Vec3d velocity = CLIENT.getPlayer().getVel();
        // Update FlySpeed in case it was changed
        //double flySpeed = Double.parseDouble(CONFIG.getConfig("FlySpeed", "1.0"));
        double flySpeed = 1.0;

        // Better air strafing
        CLIENT.getPlayer().setAirStrafingSpeed((float) flySpeed / 12);

        // Jump for jetpack
        if (CLIENT.getOptions().jumpKey.isPressed()) {
            CLIENT.getPlayer().setVel(new Vec3d(velocity.x, flySpeed, velocity.z));
        }
    }
}
