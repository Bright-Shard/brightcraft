package dev.brightshard.brightcraft.lib;

import dev.brightshard.brightcraft.lib.Event.*;
import net.minecraft.client.option.KeyBinding;
import static dev.brightshard.brightcraft.BrightCraft.LOGGER;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
    // Any dependencies we changed by enabling
    protected final Vector<HackType> changedDependencies = new Vector<>();

    public Hack(HackType id, String name, String tooltip, int key) {
        // Set the hack ID/Name/Description
        this.id = id; this.name = name; this.tooltip = tooltip; this.enabled = false;

        // Create and bind the keybinding for this hack (if it has a keybind)
        if (key != -1) {
            KeyBinding binding = new KeyBinding("brightcraft."+id, key, "category.brightcraft");
            LOGGER.info("|- Loading hack: brightcraft."+id);
            this.keybind = new Keybind(binding, this::toggle, 5);
        }

        // Update the hacks list
        HACKS.put(id, this);
    }

    // Add an event listener
    protected <EventType extends SimpleEvent> Listener listen(
            LockedBuffer<EventType> eventLock,
            Listener.SimpleCallback callback
    ) {
        try (LockedBuffer<EventType>.Lock event = eventLock.lock()) {
            Listener listener = event.readBuffer().listen(callback);
            this.listeners.add(listener);
            return listener;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected <EventType extends DataEvent<DataType>, DataType> Listener listen(
            LockedBuffer<EventType> eventLock,
            Consumer<DataType> callback
    ) {
        try (LockedBuffer<EventType>.Lock event = eventLock.lock()) {
            Listener listener = event.readBuffer().listen(callback);
            this.listeners.add(listener);
            return listener;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected <EventType extends ReturnableEvent<ReturnType>, ReturnType> Listener listen(
            LockedBuffer<EventType> eventLock,
            Supplier<ReturnType> callback
    ) {
        try (LockedBuffer<EventType>.Lock event = eventLock.lock()) {
            Listener listener = event.readBuffer().listen(callback);
            this.listeners.add(listener);
            return listener;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected <EventType extends FullEvent<DataType, ReturnType>, DataType, ReturnType> Listener listen(
            LockedBuffer<EventType> eventLock,
            Function<DataType, ReturnType> callback
    ) {
        try (LockedBuffer<EventType>.Lock event = eventLock.lock()) {
            Listener listener = event.readBuffer().listen(callback);
            this.listeners.add(listener);
            return listener;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

            // Disable blacklists, enable dependencies
            for (HackType id : this.blacklist) {
                Hack hack = Hack.getHack(id);
                if (hack.enabled) {
                    hack.disable();
                }
            }
            for (HackType id : this.dependencies) {
                Hack hack = Hack.getHack(id);
                if (!hack.enabled) {
                    hack.enable();
                    this.changedDependencies.add(hack.id);
                }
            }
            this.enabled = true;
            LOGGER.info("Enabled hack " + this.id);
        }
    }
    // Callback when the hack is disabled
    public void disable() {
        if (this.enabled) {
            for (Listener listener : this.listeners) {
                listener.bound = false;
            }

            for (HackType id : this.changedDependencies) {
                Hack.getHack(id).disable();
            }
            this.changedDependencies.clear();

            this.enabled = false;
            LOGGER.info("Disabled hack " + this.id);
        }
    }
}
