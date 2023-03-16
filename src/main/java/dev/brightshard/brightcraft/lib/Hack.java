package dev.brightshard.brightcraft.lib;

import dev.brightshard.brightcraft.lib.Event.Event;
import dev.brightshard.brightcraft.lib.Event.Listener;
import net.minecraft.client.option.KeyBinding;
import static dev.brightshard.brightcraft.BrightCraft.LOGGER;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

public abstract class Hack {
    // STATIC MEMBERS/FIELDS

    // IDs for all the hacks
    public enum HackType {
        Antikick,
        Fly,
        Fullbright,
        Instabreak,
        Jetpack,
        NoClip,
        NoFallDamage,
        Speed,
        XRay
    }
    // Hashtable for getting various hacks
    protected static Hashtable<HackType, Hack> HACKS = new Hashtable<>();
    // Static methods across all Hacks
    public static Hack getHack(HackType id) {
        return HACKS.get(id);
    }
    public static Collection<Hack> getHacks() {
        return HACKS.values();
    }
    public static void enableHacks() {
        for (Hack hack : HACKS.values()) {
            hack.enable();
        }
    }
    public static void disableHacks() {
        for (Hack hack : HACKS.values()) {
            hack.disable();
        }
    }



    // NON-STATIC MEMBERS/FIELDS

    // Hack info: Name/ID/Description
    public HackType id;
    public String name;
    public String tooltip;
    public boolean enabled;
    public Keybind keybind;

    // All the listeners this hack uses
    protected final Vector<Listener> listeners = new Vector<>();
    
    // Any hacks the hack can't work with or needs to work correctly
    protected final Vector<HackType> blacklist = new Vector<>();
    protected final Vector<HackType> dependencies = new Vector<>();
    // Any hacks that were changed when this hack was enabled
    protected final Vector<HackType> changed = new Vector<>();

    public Hack(HackType id, String name, String tooltip, int key) {
        // Set the hack ID/Name/Description
        this.id = id; this.name = name; this.tooltip = tooltip; this.enabled = false;

        // Create and bind the keybinding for this hack (if it has a keybind)
        if (key != -1) {
            KeyBinding binding = new KeyBinding("brightcraft."+id, key, "category.brightcraft");
            LOGGER.info("brightcraft."+id);
            this.keybind = new Keybind(binding, this::toggle, 5);
        }

        // Update the hacks list
        HACKS.put(id, this);
    }

    // Add an event listener
    // Variants:
    //  1. Callback type: Can accept an EventHandler, or no arguments
    //  2. EventHandler type: Can start out enabled or disabled
    protected <EventType extends Event> Listener bindEvent(LockedBuffer<EventType> eventLock, Listener.BlankCallback callback) {
        Event event = eventLock.lock();
        Listener listener = Listener.fromCallback(callback);
        event.addListener(listener);
        this.listeners.add(listener);
        eventLock.unlock();
        return listener;
    }
    protected <EventType extends Event> Listener bindDisabledEvent(LockedBuffer<EventType> eventLock, Listener.BlankCallback callback) {
        Event event = eventLock.lock();
        Listener listener = Listener.fromCallback(callback, false);
        event.addListener(listener);
        this.listeners.add(listener);
        eventLock.unlock();
        return listener;
    }
    protected <EventType extends Event> Listener bindEvent(LockedBuffer<EventType> eventLock, Listener.ParameterizedCallback callback) {
        Event event = eventLock.lock();
        Listener listener = Listener.fromCallback(callback);
        event.addListener(listener);
        this.listeners.add(listener);
        eventLock.unlock();
        return listener;
    }
    protected <EventType extends Event> Listener bindDisabledEvent(LockedBuffer<EventType> eventLock, Listener.ParameterizedCallback callback) {
        Event event = eventLock.lock();
        Listener listener = Listener.fromCallback(callback, false);
        event.addListener(listener);
        this.listeners.add(listener);
        eventLock.unlock();
        return listener;
    }

    // Blacklist a hack
    protected void addBlacklist(HackType hack) {
        this.blacklist.add(hack);
    }

    // Depend on a hack
    protected void addDependency(HackType hack) {
        this.dependencies.add(hack);
    }

    // Toggle if the hack is enabled/disabled, usually from a keypress
    public void toggle() {
        if (!this.enabled) {
            this.enable();
        } else {
            this.disable();
        }
    }

    // Callback when the hack is enabled
    public void enable() {
        if (!this.enabled) {
            for (Listener listener : this.listeners) {
                listener.bound = listener.defaultBind;
            }

            // Turn on any dependencies & off any blacklisted hacks
            for (HackType id : this.dependencies) {
                Hack hack = Hack.getHack(id);
                if (!hack.enabled) {
                    this.changed.add(id);
                    hack.enable();
                }
            }
            for (HackType id : this.blacklist) {
                Hack hack = Hack.getHack(id);
                if (hack.enabled) {
                    this.changed.add(id);
                    hack.disable();
                }
            }
            this.enabled = true;
        }
    }
    // Callback when the hack is disabled
    public void disable() {
        if (this.enabled) {
            for (Listener listener : this.listeners) {
                listener.bound = false;
            }

            // Reset any hacks this hack changed by turning on
            for (HackType hack : this.changed) {
                LOGGER.info("Resetting changed hack " + hack);
                Hack.getHack(hack).toggle();
            }
            this.changed.clear();
            this.enabled = false;
        }
    }
}
