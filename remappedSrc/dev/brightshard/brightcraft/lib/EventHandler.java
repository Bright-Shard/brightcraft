package dev.brightshard.brightcraft.lib;

import dev.brightshard.brightcraft.BrightCraft;

public abstract class EventHandler {
    public boolean bound = true;

    public EventHandler(EventManager.Event event) {
        BrightCraft.EVENTS.bind(event, this);
    }

    // Bind/release the handler to the event
    public void bind() {
        this.bound = true;
    }
    public void release() {
        this.bound = false;
    }

    public abstract <DataType> void fire(EventData<DataType> data);
    public abstract void fire();
    public abstract <DataType, ResponseType> ResponseType fireWithResponse(EventData<DataType> data);
    public abstract <ResponseType> ResponseType fireWithResponse();
}
