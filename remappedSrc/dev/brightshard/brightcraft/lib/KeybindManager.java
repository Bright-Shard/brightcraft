package dev.brightshard.brightcraft.lib;

import java.util.Vector;
import dev.brightshard.brightcraft.BrightCraft;

public class KeybindManager {
    private final Vector<Keybind> keybinds;

    public KeybindManager() {
        this.keybinds = new Vector<Keybind>();
    }

    public void registerKeybind(Keybind keybind) {
        this.keybinds.add(keybind);
    }

    private void tick() {
        if (BrightCraft.ENABLED) {
            for (Keybind key : this.keybinds) {
                // If the key was pressed, run its corresponding callback
                key.fireIfPressed();
            }
        }
    }
}
