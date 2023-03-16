package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;

import org.lwjgl.glfw.GLFW;

public class NoClip extends Hack {
    public NoClip() {
        super(
                HackType.NoClip,
                "Noclip",
                "Clip through blocks. This ONLY works on vanilla servers and single-player worlds\nAlso enables \"Fly\"",
                GLFW.GLFW_KEY_N
        );
        this.addDependency(HackType.Fly);
    }
}
