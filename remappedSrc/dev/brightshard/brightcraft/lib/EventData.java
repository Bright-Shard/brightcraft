package dev.brightshard.brightcraft.lib;

import static dev.brightshard.brightcraft.Main.LOGGER;

public class EventData<DataType> {
    private final DataType data;
    public EventData(DataType data) {
        this.data = data;
    }
    public <Type> Type getData() {
        // Yes this is EXTREMELY unsafe, stfu
        try {
            @SuppressWarnings("unchecked")
            Type returnData = (Type) this.data;
            return returnData;
        } catch (ClassCastException e) {
            LOGGER.error("Error converting event data type!" + this.data + " " + e);
            throw new RuntimeException(
                    "BrightCraft: EventData error: Error converting event data type: " + e
            );
        }
    }
}
