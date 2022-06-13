package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventHandler;
import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class NoFallDamage extends Hack {
    public NoFallDamage() {
        super("NoFallDamage", "No Fall Damage", "Don't take any fall damage", GLFW.GLFW_KEY_G);
        NoFallDamage instance = this;
        this.handlers.add(new EventHandler(instance.id, "tick") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                instance.tick();
            }
        });
    }

    private void tick() {
        Vec3d velocity = player.getVel();
        // Just flying downwards, and not quickly enough to take damage
        if (player.flying() && player.sneaking() && velocity.y > -0.5) {
            return;
        }
        player.setFallDistance(0);
        player.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }
}
