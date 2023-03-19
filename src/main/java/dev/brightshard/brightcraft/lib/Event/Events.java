package dev.brightshard.brightcraft.lib.Event;

import dev.brightshard.brightcraft.hacks.Instabreak;
import dev.brightshard.brightcraft.lib.LockedBuffer;
import net.minecraft.block.Block;

public class Events {
    // Events that don't pass any data
    public static final LockedBuffer<SimpleEvent> Tick = new LockedBuffer<>(new SimpleEvent());
    public static final LockedBuffer<SimpleEvent> PreTick = new LockedBuffer<>(new SimpleEvent());
    public static final LockedBuffer<SimpleEvent> WorldJoined = new LockedBuffer<>(new SimpleEvent());
    public static final LockedBuffer<SimpleEvent> WorldLeft = new LockedBuffer<>(new SimpleEvent());
    public static final LockedBuffer<SimpleEvent> BreakingProgressChanged = new LockedBuffer<>(new SimpleEvent());
    
    // Events that pass data
    public static final LockedBuffer<DataEvent<Instabreak.BlockInfo>> BlockBreaking = new LockedBuffer<>(new DataEvent<>());

    // Events that return data
    public static final LockedBuffer<ReturnableEvent<Boolean>> DisableAmbientOcclusion = new LockedBuffer<>(new ReturnableEvent<>());
    public static final LockedBuffer<ReturnableEvent<Boolean>> MaxUnderwaterVisibility = new LockedBuffer<>(new ReturnableEvent<>());

    // Events that pass and return data
    public static final LockedBuffer<FullEvent<Block, Boolean>> BlockShouldRender = new LockedBuffer<>(new FullEvent<>());
}
