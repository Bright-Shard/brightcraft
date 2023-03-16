package dev.brightshard.brightcraft.lib.Event;

public class Listener {
    public boolean bound;
    public final boolean defaultBind;
    public boolean acceptsParameter;

    protected Listener(boolean defaultBind, boolean acceptsParameter) {
        this.defaultBind = defaultBind;
        this.bound = defaultBind;
        this.acceptsParameter = acceptsParameter;
    }
    protected Listener(boolean acceptsParameter) {
        this.defaultBind = true;
        this.bound = false;
        this.acceptsParameter = acceptsParameter;
    }

    public interface ParameterizedCallback {
        void execute(EventDataBuffer data);
    }
    public interface BlankCallback {
        void execute();
    }

    public static class ParameterizedListener extends Listener {
        private final ParameterizedCallback callback;

        public ParameterizedListener(ParameterizedCallback callback) {
            super(true);
            this.callback = callback;
        }
        public ParameterizedListener(ParameterizedCallback callback, boolean defaultBind) {
            super(defaultBind, true);
            this.callback = callback;
        }
        public void execute(EventDataBuffer data) {
            this.callback.execute(data);
        }
    }

    public static class BlankListener extends Listener {
        private final BlankCallback callback;

        public BlankListener(BlankCallback callback) {
            super(false);
            this.callback = callback;
        }
        public BlankListener(BlankCallback callback, boolean defaultBind) {
            super(defaultBind, false);
            this.callback = callback;
        }
        public void execute() {
            this.callback.execute();
        }
    }

    public static Listener fromCallback(BlankCallback callback) {
        return new BlankListener(callback);
    }
    public static Listener fromCallback(BlankCallback callback, boolean defaultBind) {
        return new BlankListener(callback, defaultBind);
    }
    public static Listener fromCallback(ParameterizedCallback callback) {
        return new ParameterizedListener(callback);
    }
    public static Listener fromCallback(ParameterizedCallback callback, boolean defaultBind) {
        return new ParameterizedListener(callback, defaultBind);
    }
}
