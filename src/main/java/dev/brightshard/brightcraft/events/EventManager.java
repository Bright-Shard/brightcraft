package dev.brightshard.brightcraft.events;

import dev.brightshard.brightcraft.Main;

import java.util.Hashtable;

public class EventManager {
    private static final Hashtable<String, Hashtable<String, EventHandler>> events = new Hashtable<>();

    public static void bindEvent(EventHandler handler) {
        Hashtable<String, EventHandler> event = prepareEvent(handler.getEvent());

        event.put(handler.getID(), handler);
    }
    public static void releaseEvent(EventHandler handler) {
        Hashtable<String, EventHandler> event = prepareEvent(handler.getEvent());

        event.remove(handler.getID());
    }
    public static <T, R> void fireEvent(String eventID, EventData<T, R> eventData) {
        Hashtable<String, EventHandler> event = prepareEvent(eventID);

        for (EventHandler handler : event.values()) {
            handler.fire(eventData);
        }
    }
    public static void fireEvent(String eventID) {
        Hashtable<String, EventHandler> event = prepareEvent(eventID);

        for (EventHandler handler : event.values()) {
            handler.fire(null);
        }
    }

    private static Hashtable<String, EventHandler> prepareEvent(String event) {
        events.computeIfAbsent(event, k -> new Hashtable<>());
        return events.get(event);
    }
}
