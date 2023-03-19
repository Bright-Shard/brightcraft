package dev.brightshard.brightcraft.lib.Event;

import java.util.Vector;

public class SimpleEvent {
    private final Vector<Listener.SimpleListener> listeners = new Vector<>();

    public void fire() {
        for (Listener.SimpleListener listener : this.listeners) {
            if (listener.bound) {
                listener.callback.execute();
            }
        }
    }

    public Listener listen(Listener.SimpleCallback callback) {
        Listener.SimpleListener listener = new Listener.SimpleListener(callback, true, false);
        this.listeners.add(listener);
        return listener;
    }
}
