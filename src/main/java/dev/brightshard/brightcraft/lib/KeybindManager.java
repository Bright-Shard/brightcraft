package dev.brightshard.brightcraft.lib;

import java.util.Vector;

import dev.brightshard.brightcraft.lib.Event.*;

public class KeybindManager {
    private final Vector<Keybind> keybinds;

    public KeybindManager() {
        this.keybinds = new Vector<>();
        try (LockedBuffer<SimpleEvent>.Lock lock = Events.Tick.lock()) {
            Listener listener = lock.readBuffer().listen(this::tick);
            listener.bound = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
