package dev.brightshard.brightcraft.events;

import java.util.Hashtable;

public class EventManager {
    private static final Hashtable<String, Hashtable<String, EventHandler>> events = new Hashtable<>();

    public static void bindEvent(EventHandler handler) {
        prepareEvent(handler.getEvent());

        events.get(handler.getEvent()).put(handler.getID(), handler);
    }
    public static void releaseEvent(EventHandler handler) {
        prepareEvent(handler.getEvent());

        events.get(handler.getEvent()).remove(handler.getID());
    }
    public static <T, R> void fireEvent(String event, EventData<T, R> eventData) {
        prepareEvent(event);

        for (EventHandler handler : events.get(event).values()) {
            handler.fire(eventData);
        }
    }
    public static void fireEvent(String event) {
        prepareEvent(event);

        for (EventHandler handler : events.get(event).values()) {
            handler.fire(null);
        }
    }

    private static void prepareEvent(String event) {
        events.computeIfAbsent(event, k -> new Hashtable<>());
    }
}
