package dev.brightshard.brightcraft.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class EventManager {
    private static final EventManager instance = new EventManager();

    public interface eventHandler {
        void onEvent(Object firedBy);
    }

    private final Hashtable<String, ArrayList<eventHandler>> events = new Hashtable<>();

    public EventManager() {

    }
    public static EventManager getInstance() {
        return instance;
    }

    public void bindEvent(String event, eventHandler handler) {
        ArrayList<eventHandler> oldEvents = this.events.get(event);
        oldEvents.add(handler);
        this.events.put(event, oldEvents);
    }
    public void fireEvent(String event, Object firedBy) {
        for (eventHandler handler : this.events.get(event)) {
            handler.onEvent(firedBy);
        }
    }
}
