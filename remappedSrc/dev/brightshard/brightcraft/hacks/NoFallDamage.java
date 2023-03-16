package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.EventData;
import dev.brightshard.brightcraft.lib.EventHandler;
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
        Vec3d velocity = CLIENT.getPlayer().getVel();
        // Just flying downwards, and not quickly enough to take damage
        if (CLIENT.getPlayer().flying() && CLIENT.getPlayer().sneaking() && velocity.y > -0.5) {
            return;
        }
        CLIENT.getPlayer().setFallDistance(0);
        CLIENT.getPlayer().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }
}
