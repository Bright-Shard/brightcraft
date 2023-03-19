package dev.brightshard.brightcraft.lib.Event;

import java.util.function.Consumer;

public class DataEvent<DataType> {
    private Listener.DataListener<DataType> listener = null;

    public void fire(DataType data) {
        if (this.listener.bound) {
            this.listener.callback.accept(data);
        }
    }

    public Listener listen(Consumer<DataType> callback) {
        if (this.listener == null) {
            Listener.DataListener<DataType> listener = new Listener.DataListener<>(
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
