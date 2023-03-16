package dev.brightshard.brightcraft.lib.Event;

import static dev.brightshard.brightcraft.BrightCraft.LOGGER;

public class EventDataBuffer {
    private Object data;

    @SuppressWarnings("unchecked")
    public <ExpectedType> ExpectedType getValue() {
        try {
            ExpectedType result = (ExpectedType) this.data;
            this.data = null;
            return result;
        } catch (Exception e) {
            LOGGER.error("Failed to cast EventDataBuffer data!");
            LOGGER.info("EventType data type: " + this.data.getClass());
            throw new RuntimeException("EventType data type: " + this.data.getClass());
        }
    }

    public void setValue(Object data) {
        this.data = data;
    }
}
