package dev.brightshard.brightcraft.lib.Event;

public enum EventType {
    // Each game tick (only fires if BrightCraft is enabled)
    Tick(false),
    // The start of each game tick (only fires if BrightCraft is enabled)
    PreTick(false),
    // When the player exits a world or server
    WorldLeft(false),
    // When the player joins a world or server
    WorldJoined(false),
    // When a block starts breaking
    BlockBreaking(true),
    // When the block's breaking progress updates
    BreakingProgressChanged(true),
    // Whether Ambient Occlusion should be disabled
    DisableAmbientOcclusion(true),
    MaxUnderwaterVisibility(true),
    // If a block should render
    BlockShouldRender(true);

    public final boolean singleEvent;
    EventType(boolean singleEvent) {
        this.singleEvent = singleEvent;
    }
}
