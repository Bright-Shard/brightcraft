package dev.brightshard.brightcraft.lib.Event;

import java.util.function.Supplier;

public class ReturnableEvent<ReturnType> {
    private Listener.ReturnableListener<ReturnType> listener = null;

    public ReturnType fire() {
        if (this.listener.bound) {
            return this.listener.callback.get();
        }
        return null;
    }

    public Listener listen(Supplier<ReturnType> callback) {
        if (this.listener == null) {
            Listener.ReturnableListener<ReturnType> listener = new Listener.ReturnableListener<>(
                    callback,
                    true,
                    false
            );
            this.listener = listener;
            return listener;
        } else {
            throw new RuntimeException("ReturnableEvent error: Attempted to bind multiple listeners!");
        }
    }
}
