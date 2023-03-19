package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;
import dev.brightshard.brightcraft.lib.Event.Events;
import static dev.brightshard.brightcraft.BrightCraft.*;

import net.minecraft.util.math.Vec3d;

public class Antikick extends Hack {
    private int tickCounter = 40;

    public Antikick() {
        super(HackType.Antikick, "Antikick", "Tries to prevent the player from being kicked", -1);
        this.listen(Events.PreTick, this::tick);
    }

    private void tick() {
        --tickCounter;
        assert RAWCLIENT.player != null;
        Vec3d velocity = RAWCLIENT.player.getVelocity();

        // Timing:
        // TickCounter 40: Start loop
        // TickCounter 0: Shift player down (making them fall prevents being kicked for flight)
        // TickCounter -1: Shift player back up (so their movement isn't interrupted) and reset TickCounter
        // If the player ever hits the ground, or descends quickly, reset TickCounter.
        if (tickCounter == 0) {
            PLAYER.velocity = new Vec3d(velocity.x, -0.4, velocity.z);
            PLAYER.blockMovement = true;
        } else if (tickCounter == -1) {
            PLAYER.velocity = new Vec3d(velocity.x, 0.4, velocity.z);
            tickCounter = 40;
            PLAYER.blockMovement = false;
        } else if (velocity.y <= -0.4 || RAWCLIENT.player.isOnGround()) {
            tickCounter = 40;
        }
    }

    @Override
    public void enable() {
        super.enable();
        this.tickCounter = 40;
    }
}
