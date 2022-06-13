package dev.brightshard.brightcraft.managers;

import dev.brightshard.brightcraft.Main;
import dev.brightshard.brightcraft.lib.Config;
import dev.brightshard.brightcraft.lib.Hack;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;

import static dev.brightshard.brightcraft.Main.LOGGER;
import dev.brightshard.brightcraft.ui.SettingsMenu;
import org.lwjgl.glfw.GLFW;

public class KeyBindManager {
    private final Config config = Config.getInstance();
    private KeyBinding UIKeybind;
    private static final KeyBindManager instance = new KeyBindManager();

    public void registerKeyBinds() {
        for (Hack hack : Hack.getHacks()) {
            String id = hack.id;
            KeyBinding keybind = hack.keybind;
            LOGGER.info("Adding keybind for cheat "+id);
            hack.keybind = KeyBindingHelper.registerKeyBinding(keybind);
        }
        LOGGER.info("Adding keybind to open UI");
        UIKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("brightcraft.keybinds.UI",
                GLFW.GLFW_KEY_RIGHT_SHIFT, "category.brightcraft"));
    }

    public void tick() {
        if (!Main.getInstance().ready()) {
            return;
        }
        for (Hack hack : Hack.getHacks()) {
            if (hack.cooldown > 0) {
                --hack.cooldown;
                continue;
            }
            if (hack.keybind.isPressed()) {
                LOGGER.info("Hack "+hack.id+" was toggled via keybind");
                config.toggleHack(hack.id);
                hack.startCooldown();
            }
        }
        if (UIKeybind.isPressed()) {
            Main.getInstance().getClient().setScreen(SettingsMenu.getInstance());
        }
    }

    public static KeyBindManager getInstance() {
        return instance;
    }
}
