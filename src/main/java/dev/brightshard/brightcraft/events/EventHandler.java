package dev.brightshard.brightcraft.events;

public abstract class EventHandler {
    private final String id;
    private final String event;

    public EventHandler(String id, String event) {
        this.id = id;
        this.event = event;
    }

    public abstract <DataType, CIRType> void fire(EventData<DataType, CIRType> data);

    public String getID() {
        return this.id;
    }
    public String getEvent() {
        return this.event;
    }
}
