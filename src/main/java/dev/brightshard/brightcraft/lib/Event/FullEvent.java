package dev.brightshard.brightcraft.lib.Event;

import java.util.function.Function;

public class FullEvent<DataType, ReturnType> {
    private Listener.FullListener<DataType, ReturnType> listener = null;

    public ReturnType fire(DataType data) {
        if (this.listener.bound) {
            return this.listener.callback.apply(data);
        }
        return null;
    }

    public Listener listen(Function<DataType, ReturnType> callback) {
        if (this.listener == null) {
            Listener.FullListener<DataType, ReturnType> listener = new Listener.FullListener<>(
                    callback,
                    true,
                    false
            );
            this.listener = listener;
            return listener;
        } else {
            throw new RuntimeException("FullEvent error: Attempted to bind multiple listeners!");
        }
    }
}
