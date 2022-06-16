package dev.brightshard.brightcraft.lib;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventHandler;
import dev.brightshard.brightcraft.events.EventManager;
import net.minecraft.util.math.Vec3d;

import static dev.brightshard.brightcraft.Main.CLIENT;
import static dev.brightshard.brightcraft.Main.LOGGER;

public class Antikick {
    private static final double fallDistance = 0.4;
    private static final int tickCounterMax = 40;
    private static int tickCounter = 10;
    private static boolean bound;
    private static boolean blockMovement;
    private static final EventHandler handler = new EventHandler("Antikick", "pretick") {
        @Override
        public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
            // Player velocity
            Vec3d velocity = CLIENT.getPlayer().getVel();
            // Decrement the counter once per tick
            --tickCounter;

            if (tickCounter == -2) { // If the player has moved up and down already, reset the counter
                reset();
            } else if (tickCounter == -1) { // If the player has already moved down, move it back up
                CLIENT.getPlayerRaw().setVelocity(new Vec3d(velocity.x, fallDistance, velocity.z));
            } else if (tickCounter == 0) { // When the counter hits 0, move the player down
                CLIENT.getPlayerRaw().setVelocity(new Vec3d(velocity.x, -fallDistance, velocity.z));
                blockMovement = true;
            } else if (velocity.y <= -fallDistance) { // If the player is falling, reset
                reset();
            }
        }
    };

    public static void reset() {
        tickCounter = tickCounterMax;
        blockMovement = false;
    }

    public static boolean shouldBlockMovement() {
        return blockMovement;
    }

    public static void enable() {
        if (bound) return;
        reset();
        EventManager.bindEvent(handler);
        bound = true;
    }
    public static void disable() {
        if (!bound) return;
        reset();
        EventManager.releaseEvent(handler);
        bound = false;
    }
}
