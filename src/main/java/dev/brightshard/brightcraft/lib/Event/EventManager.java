package dev.brightshard.brightcraft.lib.Event;

import dev.brightshard.brightcraft.lib.LockedBuffer;

import java.util.Hashtable;

import static dev.brightshard.brightcraft.BrightCraft.LOGGER;

public class EventManager {
    private final Hashtable<EventType, LockedBuffer<Event>> events = new Hashtable<>();

    // Listen to an event
    public void listen(EventType eventType, Listener listener) {
        try {
            Event event = this.prepEvent(eventType).lock();
            event.addListener(listener);
            this.prepEvent(eventType).unlock();
        } catch (InterruptedException e) {
            LOGGER.error("EventManager: Failed to lock event " + eventType);
            throw new RuntimeException(e);
        }
    }

    // Fire an event
    public void fire(EventType eventType) {
        try {
            Event event = this.prepEvent(eventType).lock();
            event.fire();
            this.prepEvent(eventType).unlock();
        } catch (InterruptedException e) {
            LOGGER.error("EventManager: Failed to lock event " + eventType);
            throw new RuntimeException(e);
        }
    }
    public void fire(EventType eventType, Object data) {
        try {
            Event event = this.prepEvent(eventType).lock();
            event.setBuffer(data);
            event.fire();
            this.prepEvent(eventType).unlock();
        } catch (InterruptedException e) {
            LOGGER.error("EventManager: Failed to lock event " + eventType);
            throw new RuntimeException(e);
        }
    }

    // Fire an event & return some data
    public <ReturnType> ReturnType fireReturnable(EventType eventType) {
        try {
            Event event = this.prepEvent(eventType).lock();
            event.fire();
            ReturnType result = event.getBuffer().getValue();
            this.prepEvent(eventType).unlock();
            LOGGER.info("Returning " + result);
            return result;
        } catch (InterruptedException e) {
            LOGGER.error("EventManager: Failed to lock event " + eventType);
            throw new RuntimeException(e);
        }
    }
    public <ReturnType> ReturnType fireReturnable(EventType eventType, Object data) {
        try {
            Event event = this.prepEvent(eventType).lock();
            event.setBuffer(data);
            event.fire();
            ReturnType result = event.getBuffer().getValue();
            this.prepEvent(eventType).unlock();
            return result;
        } catch (InterruptedException e) {
            LOGGER.error("EventManager: Failed to lock event " + eventType);
            throw new RuntimeException(e);
        }
    }

    private LockedBuffer<Event> prepEvent(EventType eventType) {
        this.events.computeIfAbsent(
                eventType,
                (eek) -> eventType.singleEvent?
                        new LockedBuffer<>(new Event.SingleEvent())
                        : new LockedBuffer<>(new Event.MultiEvent())
        );
        return this.events.get(eventType);
    }
}

//public class EventManager {
//
//    private final Hashtable<EventType, Vector<EventHandler>> events = new Hashtable<>();
//
//    // Bind an EventHandler to an EventType
//    public void bind(EventType event, EventHandler handler) {
//        this.prepEvent(event).add(handler);
//    }
//
//    // Fire all EventHandlers for an EventType
//    public void fire(EventType event) {
//        for (EventHandler handler : this.prepEvent(event)) {
//            // Make sure the handler is enabled before firing
//            if (handler.bound()) {
//                handler.execute();
//            }
//        }
//    }
//    // Same as above, but returns the data in the handler's data buffer
//    public <ReturnType> ReturnType fireReturnable(EventType event) {
//        EventDataBuffer returnBuffer = new EventDataBuffer();
//
//        for (EventHandler handler : this.prepEvent(event)) {
//            // Make sure the handler is enabled before firing
//            if (handler.bound()) {
//                handler.lock.waitForUnlock();
//                handler.lock.lock();
//
//                handler.execute();
//
//                if (handler.buffer.changed()) {
//                    returnBuffer.setValue(handler.buffer.getValue());
//                }
//                handler.lock.unlock();
//            }
//        }
//
//        return returnBuffer.getValue();
//    }
//
//    // Fire all EventHandlers for an EventType, storing data in the buffer
//    public void fireWithData(EventType event, Object data) {
//        for (EventHandler handler : this.prepEvent(event)) {
//            // Make sure the handler is enabled before firing
//            if (data == null) {
//                throw new RuntimeException("fireWithData's data is null!");
//            }
//            if (handler.bound()) {
//                handler.buffer.setValue(data);
//                handler.execute();
//            }
//        }
//    }
//
//    // The exact same as above, except the buffer's data is also returned
//    public <ReturnType> ReturnType fireWithDataReturnable(EventType event, Object data) {
//        for (EventHandler handler : this.prepEvent(event)) {
//            if (handler.bound()) {
//                handler.lock.waitForUnlock();
//                handler.lock.lock();
//
//                handler.buffer.setValue(data);
//                handler.buffer.setUnchanged();
//                handler.execute();
//
//                if (handler.buffer.changed()) {
//                    return handler.buffer.getValue();
//                }
//                handler.lock.unlock();
//            }
//        }
//        return null;
//    }
//
//    private Vector<EventHandler> prepEvent(EventType event) {
//        // Make sure the events list has an entry for this event;
//        //  if not, make an empty vector for it
//        this.events.computeIfAbsent(event, eek -> new Vector<>());
//        // Return the entry from the events list for this event
//        return this.events.get(event);
//    }
//}
