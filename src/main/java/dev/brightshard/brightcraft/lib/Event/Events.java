package dev.brightshard.brightcraft.lib.Event;

import dev.brightshard.brightcraft.lib.LockedBuffer;

public class Events {
    // Events with unlimited listeners
    public static final LockedBuffer<Event.MultiEvent> Tick = new LockedBuffer<>(new Event.MultiEvent());
    public static final LockedBuffer<Event.MultiEvent> PreTick = new LockedBuffer<>(new Event.MultiEvent());
    public static final LockedBuffer<Event.MultiEvent> WorldJoined = new LockedBuffer<>(new Event.MultiEvent());
    public static final LockedBuffer<Event.MultiEvent> WorldLeft = new LockedBuffer<>(new Event.MultiEvent());
    
    // Events with only one listener
    public static final LockedBuffer<Event.SingleEvent> BlockBreaking = new LockedBuffer<>(new Event.SingleEvent());
    public static final LockedBuffer<Event.SingleEvent> BreakingProgressChanged = new LockedBuffer<>(new Event.SingleEvent());
    public static final LockedBuffer<Event.SingleEvent> DisableAmbientOcclusion = new LockedBuffer<>(new Event.SingleEvent());
    public static final LockedBuffer<Event.SingleEvent> MaxUnderwaterVisibility = new LockedBuffer<>(new Event.SingleEvent());
    public static final LockedBuffer<Event.SingleEvent> BlockShouldRender = new LockedBuffer<>(new Event.SingleEvent());
}
