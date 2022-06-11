package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventHandler;
import dev.brightshard.brightcraft.lib.Hack;

import org.lwjgl.glfw.GLFW;

public class NoClip extends Hack {
    private boolean flyWasEnabled = true;
    private final Hack flyHack = Hack.getHackById("Fly");
    public NoClip() {
        super("NoClip", "Noclip", "Clip through blocks. This ONLY works on vanilla servers and single-player worlds\nAlso enables \"Fly\"", GLFW.GLFW_KEY_N);
        NoClip instance = this;
        this.handlers.add(new EventHandler(instance.id, "tick") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                player.setNoClip(true);
            }
        });
    }

    @Override
    public void onEnable() {
        this.flyWasEnabled = this.flyHack.enabled();
        this.flyHack.enable();
    }
    @Override
    public void onDisable() {
        if (!this.flyWasEnabled) {
            this.flyHack.disable();
            this.flyWasEnabled = true;
        }
    }
}
