package dev.brightshard.brightcraft.lib;

import dev.brightshard.brightcraft.Main;
import dev.brightshard.brightcraft.managers.ClientManager;
import dev.brightshard.brightcraft.managers.PlayerManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

public abstract class Hack {
    // Constants
    protected static final Config CONFIG = Config.getInstance();
    protected static final Logger LOGGER = Main.LOGGER;
    protected static ClientManager CLIENT = Main.CLIENT;
    protected static MinecraftClient MC = Main.MC;
    protected static Hashtable<String, Hack> hacks = new Hashtable<>();

    public String id;
    public String name;
    public String tooltip;
    public int cooldown;
    protected int key;
    public KeyBinding keybind;
    protected final ArrayList<EventHandler> handlers = new ArrayList<>();
    static class EmptyHackClass extends Hack {
        public EmptyHackClass() {
            super();
        }
    }

    public Hack(String id, String name, String tooltip, int key) {
        this.id = id; this.name = name; this.tooltip = tooltip; this.key = key;
        this.keybind = new KeyBinding("brightcraft.keybinds."+id, key, "category.brightcraft");
        hacks.put(id, this);
    }
    public Hack() {
        this.id = "empty";
        this.name = "empty";
    }

    public void startCooldown() {
        this.cooldown = 5;
    }

    public boolean enabled() {
        return CONFIG.getConfig(this.id).equals("true");
    }
    public void enable() {
        if (!this.enabled()) {
            CONFIG.setConfig(this.id, "true");
            this.onEnable();
            new Chat(this);
        }
    }
    public void disable() {
        if (this.enabled()) {
            CONFIG.setConfig(this.id, "false");
            this.onDisable();
            new Chat(this);
        }
    }

    public void onEnable() {
        for (EventHandler handler : this.handlers) {
            EventManager.bindEvent(handler);
        }
    }
    public void onDisable() {
        for (EventHandler handler : this.handlers) {
            EventManager.releaseEvent(handler);
        }
    }

    public static Collection<Hack> getHacks() {
        return hacks.values();
    }
    public static PlayerManager getPlayer() {
        return CLIENT.getPlayer();
    }
    public static Hack getHackById(String id) {
        if (hacks == null) {
            return new EmptyHackClass();
        }
        Hack hack = hacks.get(id);
        return (hack != null ? hack : new EmptyHackClass());
    }

    public static void enableHacks() {
        for (Hack hack : hacks.values()) {
            if (hack.enabled()) {
                hack.onEnable();
            }
        }
    }
    public static void disableHacks() {
        for (Hack hack : hacks.values()) {
            if (hack.enabled()) {
                hack.onDisable();
            }
        }
    }
}
