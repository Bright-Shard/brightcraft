package dev.brightshard.brightcraft.lib;

import java.util.Hashtable;
import java.util.Vector;

public class EventManager {
    public enum Event {
        Tick(null),
        DisableAmbientOcclusion(1),
        BlockShouldRender(1);

        private final Integer maxHandlers;
        Event(Integer maxHandlers) {
            this.maxHandlers = maxHandlers;
        }
    }

    private final Hashtable<Event, Vector<EventHandler>> events = new Hashtable<>();

    public void bind(Event event, EventHandler handler) {
        // Add the handler to the list of handlers, if it isn't maxed
        Vector<EventHandler> handlers = this.prepEvent(event);
        if ((handlers.size() - 1) < event.maxHandlers) {
            handlers.add(handler);
        } else {
            throw new RuntimeException(
                    "BrightCraft: EventManager error: Attempted to bind too many handlers to " + event
            );
        }
    }
    public <DataType> void fire(Event event, EventData<DataType> data) {
        // Fire all handlers, if they're bound
        for (EventHandler handler : this.prepEvent(event)) {
            if (handler.bound) {
                handler.fire(data);
            }
        }
    }
    public void fire(Event event) {
        // Fire all handlers, if they're bound
        for (EventHandler handler : this.prepEvent(event)) {
            if (handler.bound) {
                handler.fire();
            }
        }
    }
    public <DataType, ResponseType> ResponseType fireWithResponse(Event event, EventData<DataType> data) {
        // We can only get a response if the event only allows 1 listener
        // Otherwise there's no way to know which handler to get the response from
        if (event.maxHandlers == 1) {
            return this.prepEvent(event).get(0).fireWithResponse(data);
        } else {
            throw new RuntimeException(
                    "BrightCraft: EventManager error: Attempted to get response from event with >1 handlers"
            );
        }
    }
    public <ResponseType> ResponseType fireWithResponse(Event event) {
        // We can only get a response if the event only allows 1 listener
        // Otherwise there's no way to know which handler to get the response from
        if (event.maxHandlers == 1) {
            return this.prepEvent(event).get(0).fireWithResponse();
        } else {
            throw new RuntimeException(
                    "BrightCraft: EventManager error: Attempted to get response from event with >1 handlers"
            );
        }
    }

    private Vector<EventHandler> prepEvent(Event event) {
        // Make sure the events list has an entry for this event;
        //  if not, make an empty vector for it
        this.events.computeIfAbsent(event, eek -> new Vector<>());
        // Return the entry from the events list for this event
        return this.events.get(event);
    }
}
