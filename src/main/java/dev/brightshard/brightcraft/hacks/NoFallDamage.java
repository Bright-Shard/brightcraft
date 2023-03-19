package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Hack;

import static dev.brightshard.brightcraft.BrightCraft.RAWCLIENT;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class NoFallDamage extends Hack {
    public NoFallDamage() {
        super(
                HackType.NoFallDamage,
                "No Fall Damage",
                "Don't take any fall damage",
                GLFW.GLFW_KEY_G
        );
        this.listen(Events.Tick, this::tick);
    }

    private void tick() {
        assert RAWCLIENT.player != null;
        Vec3d velocity = RAWCLIENT.player.getVelocity();
        // Just flying downwards, and not quickly enough to take damage
        if (RAWCLIENT.player.isSneaking() && velocity.y > -0.5) {
            return;
        }
        RAWCLIENT.player.fallDistance = 0;
        RAWCLIENT.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }
}
