package dev.brightshard.brightcraft.lib;

import dev.brightshard.brightcraft.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Hashtable;

public abstract class Hack {
    protected static PlayerManager playerManager = PlayerManager.getInstance();
    protected static MinecraftClient client = playerManager.getClient();
    protected static ClientPlayerEntity player = playerManager.getPlayer();
    protected static final Config config = Config.getInstance();
    protected static final Logger LOGGER = Main.LOGGER;
    protected static Hashtable<String, Hack> hacks = new Hashtable<>();

    public String id;
    public String name;
    public String tooltip;
    public int cooldown;
    protected int key;
    public KeyBinding keybind;

    public Hack(String id, String name, String tooltip, int key) {
        this.id = id; this.name = name; this.tooltip = tooltip; this.key = key;
        this.keybind = new KeyBinding("brightcraft.keybinds."+id, key, "category.brightcraft");
        hacks.put(id, this);
    }

    public void startCooldown() {
        this.cooldown = 5;
    }

    public boolean enabled() {
        return config.getConfig(this.id).equals("true");
    }
    public void enable() {
        boolean wasDisabled = !this.enabled();
        config.setConfig(this.id, "true");
        this.onEnable();
        if (wasDisabled) {
            new Chat(this);
        }
    }
    public void disable() {
        boolean wasEnabled = this.enabled();
        config.setConfig(this.id, "false");
        this.onDisable();
        if (wasEnabled) {
            new Chat(this);
        }
    }

    public abstract void tick();
    public abstract void onEnable();
    public abstract void onDisable();

    public static void resetClient() {
        playerManager = PlayerManager.getInstance();
        client = playerManager.getClient();
        player = playerManager.getPlayer();
    }

    public static Collection<Hack> getHacks() {
        return hacks.values();
    }
    public static Hack getHackById(String id) {
        if (hacks == null) {
            return null;
        }
        return hacks.get(id);
    }
}
