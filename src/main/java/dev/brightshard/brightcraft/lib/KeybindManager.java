package dev.brightshard.brightcraft.lib;

import java.util.Vector;

import dev.brightshard.brightcraft.lib.Event.*;

import static dev.brightshard.brightcraft.BrightCraft.EVENTS;

public class KeybindManager {
    private final Vector<Keybind> keybinds;

    public KeybindManager() {
        this.keybinds = new Vector<>();
        EVENTS.listen(EventType.Tick, Listener.fromCallback(this::tick, true));
    }

    public void registerKeybind(Keybind keybind) {
        this.keybinds.add(keybind);
    }

    private void tick() {
        for (Keybind key : this.keybinds) {
            // If the key was pressed, run its corresponding callback
            key.fireIfPressed();
        }
    }
}
