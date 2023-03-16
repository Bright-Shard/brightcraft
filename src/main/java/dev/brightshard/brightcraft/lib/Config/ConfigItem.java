package dev.brightshard.brightcraft.lib.Config;

import static dev.brightshard.brightcraft.BrightCraft.CONFIG;

public class ConfigItem <T> implements Config.SerializedConfig {
    private T value;

    public ConfigItem(String id, T value) {
        this.value = value;
        CONFIG.addConfig(id, this);
    }

    public T getRawValue() {
        return this.value;
    }
    public void setRawValue(T value) {
        this.value = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setValue(String value) {
        this.value = (T) value;
    }

    @Override
    public String getValue() {
        return String.valueOf(this.value);
    }
}
