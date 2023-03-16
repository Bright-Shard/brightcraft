/*
    Based off of the Lock class in Tuddylib, written by TudbuT
    https://github.com/TudbuT/tuddylib
    View the file here:
    https://github.com/TudbuT/tuddylib/blob/master/src/main/java/tudbut/tools/Lock.java
*/

package dev.brightshard.brightcraft.lib;

import static dev.brightshard.brightcraft.BrightCraft.LOGGER;

/*
    A generic buffer, whose contents can only be accessed while the buffer is locked.
*/

public class LockedBuffer<BufferType> {
    private final BufferType buffer;
    private boolean locked = false;

    public LockedBuffer(BufferType buffer) {
        this.buffer = buffer;
    }

    // Control the lock state
    public synchronized BufferType lock() {
        if (this.locked) {
            try {
                this.waitForUnlock();
            } catch (InterruptedException e) {
                LOGGER.error("LockedBuffer: Failed to lock buffer!");
                throw new RuntimeException(e);
            }
        }
        this.locked = true;
        return this.buffer;
    }
    public synchronized void unlock() {
        this.locked = false;
        this.notifyAll();
    }

    // Get the lock state
    public synchronized boolean isLocked() {
        return locked;
    }
    public synchronized void waitForUnlock() throws InterruptedException {
        this.wait();
    }
}
