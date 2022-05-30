package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class NoFallDamage extends Hack {
    public NoFallDamage() {
        super("NoFallDamage", "No Fall Damage", "Don't take any fall damage", GLFW.GLFW_KEY_G);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void tick() {
        Vec3d velocity = player.getVelocity();
        // Just flying downwards, and not quickly enough to take damage
        if (player.flying() && player.getPlayerEntity().isSneaking() && velocity.y > -0.5) {
            return;
        }
        player.setFallDistance(0);
        player.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }
}
