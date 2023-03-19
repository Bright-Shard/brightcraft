package dev.brightshard.brightcraft.patches;

import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Event.Listener;
import dev.brightshard.brightcraft.lib.Event.SimpleEvent;
import dev.brightshard.brightcraft.lib.Hack;
import dev.brightshard.brightcraft.lib.LockedBuffer;
import dev.brightshard.brightcraft.lib.MathTools;
import net.minecraft.util.math.Vec3d;

import static dev.brightshard.brightcraft.BrightCraft.*;

public class PatchedPlayer {
    public Vec3d velocity = Vec3d.ZERO;
    public boolean blockMovement = false;
    
    public PatchedPlayer() {
        try (LockedBuffer<SimpleEvent>.Lock lock = Events.Tick.lock()) {
            Listener listener = lock.readBuffer().listen(this::movePlayer);
            listener.bound = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void movePlayer() {
        if (this.blockMovement) {
            this.velocity = Vec3d.ZERO;
            return;
        }

        assert RAWCLIENT.player != null;
        if (Hack.getHack(Hack.HackType.Fly).enabled) {
            RAWCLIENT.player.setVelocity(this.velocity);
        } else if (this.velocity != Vec3d.ZERO) {
            RAWCLIENT.player.setVelocity(this.velocity);
        }

        this.velocity = Vec3d.ZERO;
    }
    
    // Move player
    public void moveForwards(double amount) {
        assert RAWCLIENT.player != null;
        this.velocity = MathTools.addVectors(this.velocity, RAWCLIENT.player.getRotationVector().multiply(amount));
    }
    public void moveBackwards(double amount) {
        assert RAWCLIENT.player != null;
        this.velocity = MathTools.addVectors(this.velocity, RAWCLIENT.player.getRotationVector().negate().multiply(amount));
    }
    public void moveLeft(double amount) {
        assert RAWCLIENT.player != null;
        Vec3d vel = RAWCLIENT.player.getRotationVector().rotateY((float) Math.toRadians(90)).multiply(amount);
        this.velocity = MathTools.addVectors(this.velocity, new Vec3d(vel.x, this.velocity.y, vel.z));
    }
    public void moveRight(double amount) {
        assert RAWCLIENT.player != null;
        Vec3d vel = RAWCLIENT.player.getRotationVector().rotateY((float) Math.toRadians(-90)).multiply(amount);
        this.velocity = MathTools.addVectors(this.velocity, new Vec3d(vel.x, this.velocity.y, vel.z));
    }

    // These movements don't affect vertical velocity
    public void moveForwardsFlat(double amount) {
        assert RAWCLIENT.player != null;
        Vec3d vel = RAWCLIENT.player.getRotationVector().multiply(amount);
        this.velocity = MathTools.addVectors(this.velocity, new Vec3d(vel.x, 0, vel.z));
        this.velocity = new Vec3d(this.velocity.x, RAWCLIENT.player.getVelocity().y, this.velocity.z);
    }
    public void moveBackwardsFlat(double amount) {
        assert RAWCLIENT.player != null;
        Vec3d vel = RAWCLIENT.player.getRotationVector().negate().multiply(amount);
        this.velocity = MathTools.addVectors(this.velocity, new Vec3d(vel.x, 0, vel.z));
        this.velocity = new Vec3d(this.velocity.x, RAWCLIENT.player.getVelocity().y, this.velocity.z);
    }
    public void moveLeftFlat(double amount) {
        assert RAWCLIENT.player != null;
        Vec3d vel = RAWCLIENT.player.getRotationVector().rotateY((float) Math.toRadians(90)).multiply(amount);
        this.velocity = MathTools.addVectors(this.velocity, new Vec3d(vel.x, 0, vel.z));
        this.velocity = new Vec3d(this.velocity.x, RAWCLIENT.player.getVelocity().y, this.velocity.z);
    }
    public void moveRightFlat(double amount) {
        assert RAWCLIENT.player != null;
        Vec3d vel = RAWCLIENT.player.getRotationVector().rotateY((float) Math.toRadians(-90)).multiply(amount);
        this.velocity = MathTools.addVectors(this.velocity, new Vec3d(vel.x, 0, vel.z));
        this.velocity = new Vec3d(this.velocity.x, RAWCLIENT.player.getVelocity().y, this.velocity.z);
    }
    public void moveUp(double amount) {
        this.velocity = new Vec3d(this.velocity.x, amount, this.velocity.z);
    }
    public void moveDown(double amount) {
        this.velocity = new Vec3d(this.velocity.x, -amount, this.velocity.z);
    }
}
