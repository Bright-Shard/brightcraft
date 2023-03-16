package dev.brightshard.brightcraft.lib;

import java.util.function.Supplier;
import net.minecraft.client.option.KeyBinding;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class Keybind {
    private int cooldown;
    private final int maxCooldown;
    private final Supplier<Void> callback;
    private final KeyBinding key;

    public Keybind(KeyBinding key, Supplier<Void> callback, int cooldown) {
        this.key = key;
        this.callback = callback;
        this.cooldown = cooldown;
        this.maxCooldown = cooldown;

        KeyBindingHelper.registerKeyBinding(this.key);
    }

    public void fireIfPressed() {
        if (this.cooldown == 0 && this.key.isPressed()) {
            this.callback.get();
            this.cooldown = this.maxCooldown;
        } else {
            this.cooldown -= 1;
        }
    }
}
