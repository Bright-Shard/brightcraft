package dev.brightshard.brightcraft.lib.Event;

import java.util.Vector;

/*
    NOTE:
    This should ONLY be used with a LockedBuffer.

    Minecraft *is* multithreaded, you *will* have race conditions etc in your events
    if you don't use a LockedBuffer.
 */

public abstract class Event {
    // The buffer is used to store parameters or returned data generically
    protected final EventDataBuffer buffer = new EventDataBuffer();

    // Fire the event, running all its callbacks
    public abstract void fire();
    // Add a listener to this event
    public abstract void addListener(Listener listener);

    public EventDataBuffer getBuffer() {
        return this.buffer;
    }
    public void setBuffer(Object data) {
        this.buffer.setValue(data);
    }

    // Represents an event that has one listener
    public static class SingleEvent extends Event {
        private Listener listener = null;

        @Override
        public void fire() {
            if (this.listener.acceptsParameter) {
                Listener.ParameterizedListener listener = (Listener.ParameterizedListener) this.listener;
                listener.execute(this.buffer);
            } else {
                Listener.BlankListener listener = (Listener.BlankListener) this.listener;
                listener.execute();
            }
        }

        @Override
        public void addListener(Listener listener) {
            if (this.listener == null) {
                this.listener = listener;
            } else {
                throw new RuntimeException("Attempted to add multiple listeners to a SingleEvent!");
            }
        }
    }
    // Represents an event that has many listeners
    public static class MultiEvent extends Event {
        private final Vector<Listener> listeners = new Vector<>();

        @Override
        public void fire() {
            for (Listener rawListener : this.listeners) {
                if (rawListener.acceptsParameter) {
                    Listener.ParameterizedListener listener = (Listener.ParameterizedListener) rawListener;
                    listener.execute(this.buffer);
                } else {
                    Listener.BlankListener listener = (Listener.BlankListener) rawListener;
                    listener.execute();
                }
            }
        }

        @Override
        public void addListener(Listener listener) {
            this.listeners.add(listener);
        }
    }
}