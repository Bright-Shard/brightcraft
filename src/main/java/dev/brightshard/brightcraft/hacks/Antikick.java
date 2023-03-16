package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;
import dev.brightshard.brightcraft.lib.Event.Events;
import static dev.brightshard.brightcraft.BrightCraft.CLIENT;

import net.minecraft.util.math.Vec3d;

public class Antikick extends Hack {
    private int tickCounter = 40;

    public Antikick() {
        super(HackType.Antikick, "Antikick", "Prevents being kicked by the server (in some cases)", -1);
        this.bindEvent(Events.PreTick, this::tick);
    }

    private void tick() {
        --tickCounter;
        Vec3d velocity = CLIENT.getPlayer().getVel();

        // Timing:
        // TickCounter 0: Shift player down (making them fall prevents being kicked for flight)
        // TickCounter -1: Shift player back up (so their movement isn't interrupted) and reset TickCounter
        if (tickCounter == 0) {
            CLIENT.getPlayer().setVel(new Vec3d(velocity.x, -0.4, velocity.z));
            CLIENT.getPlayer().blockMovement(true);
        } else if (tickCounter == -1) {
            CLIENT.getPlayer().setVel(new Vec3d(velocity.x, 0.4, velocity.z));
            tickCounter = 40;
            CLIENT.getPlayer().blockMovement(false);
        } else if (velocity.y <= -0.4) {
            tickCounter = 40;
        }
    }
}
