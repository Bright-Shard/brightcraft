package dev.brightshard.brightcraft.managers;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

public class EventManager {

    private final Hashtable<String, Hashtable<String, eventHandler>> events = new Hashtable<>();
    private static final EventManager instance = new EventManager();

    public interface eventHandler {
        void fire(CallbackInfoReturnable cir, Object eventData);
    }

    public static EventManager getInstance() {
        return instance;
    }

    public void bindEvent(String event, String id, eventHandler handler) {
        this.events.computeIfAbsent(event, k -> new Hashtable<>()).put(id, handler);
    }
    public void releaseEvent(String event, String id) {
        this.events.get(event).remove(id);
    }

    public void fireEvent(String event, CallbackInfoReturnable cir, Object eventData) {
        if (this.events.get(event) == null) {
            return;
        }

        Collection<eventHandler> handlers = this.events.get(event).values();
        for (eventHandler handler : handlers) {
            handler.fire(cir, eventData);
        }
    }
}
