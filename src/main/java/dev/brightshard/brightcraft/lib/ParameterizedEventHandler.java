package dev.brightshard.brightcraft.lib;

import java.util.function.Function;

public class ParameterizedEventHandler <DataType, ReturnType> {
    public boolean bound = false;
    private final Function<DataType, ReturnType> callback;

    public ParameterizedEventHandler(Function<DataType, ReturnType> callback) {
        this.callback = callback;
    }

    public ReturnType execute(DataType data) {
        return this.callback.apply(data);
    }

    public void bind() {
        this.bound = true;
    }
    public void release() {
        this.bound = false;
    }
}
