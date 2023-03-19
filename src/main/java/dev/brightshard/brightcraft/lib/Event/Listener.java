package dev.brightshard.brightcraft.lib.Event;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Listener {
    public boolean bound;
    public final boolean defaultBind;
    public final boolean acceptsParameter;

    protected Listener(boolean defaultBind, boolean bound, boolean acceptsParameter) {
        this.defaultBind = defaultBind;
        this.bound = bound;
        this.acceptsParameter = acceptsParameter;
    }

    // A listener that accepts and returns data
    public static class FullListener<DataType, ReturnType> extends Listener {
        public final Function<DataType, ReturnType> callback;
        public FullListener(Function<DataType, ReturnType> callback, boolean defaultBind, boolean bound) {
            super(defaultBind, bound, true);
            this.callback = callback;
        }
    }

    // A listener that only returns data
    public static class ReturnableListener<ReturnType> extends Listener {
        public final Supplier<ReturnType> callback;
        public ReturnableListener(Supplier<ReturnType> callback, boolean defaultBind, boolean bound) {
            super(defaultBind, bound, false);
            this.callback = callback;
        }
    }

    // A listener that only accepts data
    public static class DataListener<DataType> extends Listener {
        public final Consumer<DataType> callback;
        public DataListener(Consumer<DataType> callback, boolean defaultBind, boolean bound) {
            super(defaultBind, bound, true);
            this.callback = callback;
        }
    }

    // A listener that neither accepts nor returns data
    @FunctionalInterface
    public interface SimpleCallback {
        void execute();
    }
    public static class SimpleListener extends Listener {
        public final SimpleCallback callback;
        public SimpleListener(SimpleCallback callback, boolean defaultBind, boolean bound) {
            super(defaultBind, bound, false);
            this.callback = callback;
        }
    }
}
