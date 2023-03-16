package dev.brightshard.brightcraft.lib;

import java.util.function.Supplier;
import net.minecraft.client.option.KeyBinding;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import static dev.brightshard.brightcraft.BrightCraft.KEYBINDS;
import static dev.brightshard.brightcraft.BrightCraft.LOGGER;

public class Keybind {
    private int cooldown;
    private final int maxCooldown;
    private final Callback callback;
    private final KeyBinding key;

    public Keybind(KeyBinding key, Callback callback, int cooldown) {
        this.key = key;
        this.callback = callback;
        this.cooldown = cooldown;
        this.maxCooldown = cooldown;

        // Register the keybinding to BrightCraft + Minecraft
        KeyBindingHelper.registerKeyBinding(this.key);
        KEYBINDS.registerKeybind(this);
    }

    public void fireIfPressed() {
        // If the key is pressed, and the cooldown is over, run its callback & restart the cooldown
        if (this.cooldown == 0 && this.key.isPressed()) {
            this.callback.execute();
            this.cooldown = this.maxCooldown;
        // Otherwise, just decrement the cooldown
        } else if (this.cooldown != 0) {
            this.cooldown -= 1;
        }
    }
}
