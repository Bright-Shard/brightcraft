package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventHandler;
import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.util.math.Vec3d;

import org.lwjgl.glfw.GLFW;

public class Jetpack extends Hack {

    public Jetpack() {
        super("Jetpack", "Jetpack", "Haha jetpack go brrrrr\nRecommendation: Enable \"No Fall Damage\", too!", GLFW.GLFW_KEY_J);
        Jetpack instance = this;
        this.handlers.add(new EventHandler(instance.id, "tick") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                instance.tick();
            }
        });
    }

    @Override
    public void onEnable() {
        Hack.getHackById("Fly").disable();
        super.onEnable();
        player.flying(true);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        player.flying(false);
    }

    private void tick() {
        // Reset newVelocity
        Vec3d velocity = player.getVel();
        // Update FlySpeed in case it was changed
        double flySpeed = Double.parseDouble(config.getConfig("FlySpeed", "1.0"));

        // Better air strafing
        player.setAirStrafingSpeed((float) flySpeed / 12);

        // Jump for jetpack
        if (options.jumpKey.isPressed()) {
            player.setVel(new Vec3d(velocity.x, flySpeed, velocity.z));
        }
    }
}
