package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;

import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class NoClip extends Hack {
    public NoClip() {
        super("NoClip", "Noclip", "Clip through blocks. This ONLY works on vanilla servers and single-player worlds\nAlso enables \"Fly\"", GLFW.GLFW_KEY_N);
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(Hack.getHackById("Fly")).enable();
        playerManager.flying(true);
    }
    @Override
    public void onDisable() {

    }

    @Override
    public void tick() {
        player.noClip = true;
        playerManager.flying(true);
    }
}
