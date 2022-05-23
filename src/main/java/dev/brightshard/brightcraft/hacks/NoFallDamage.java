package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

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
        if (playerManager.getVelocity().y < -0.3) {
            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
    }
}
