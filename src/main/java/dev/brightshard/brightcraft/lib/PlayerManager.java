package dev.brightshard.brightcraft.lib;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

import dev.brightshard.brightcraft.mixin.ClientPlayerInteractionManagerMixin;

import java.util.Objects;

public class PlayerManager {
    private ClientPlayerEntity player = null;
    private MinecraftClient client = null;
    private Vec3d velocity = new Vec3d(0, 0, 0);
    private boolean flying = false;
    private boolean block = false;
    private double up = 0;
    private double down = 0;
    private final double turnAngle = Math.toRadians(90);
    private static final PlayerManager instance = new PlayerManager();

    public void tick(MinecraftClient client) {
        if (client.player != null) {
            if (client != this.client || client.player != this.player) {
                this.client = client;
                this.player = client.player;
                Hack.resetClient();
            }
            this.velocity = client.player.getVelocity();
            if (!Objects.requireNonNull(Hack.getHackById("Fly")).enabled() && !Objects.requireNonNull(Hack.getHackById("NoClip")).enabled()) {
                this.flying(false);
            }
        }  else {
            this.player = null;
            this.client = null;
            this.velocity = null;
        }
        this.block = false;
    }

    public ClientPlayerEntity getPlayer() {
        return this.player;
    }
    public MinecraftClient getClient() {
        return this.client;
    }
    public Vec3d getVelocity() {
        return this.velocity;
    }
    public Vec3d getRot() {
        return this.player.getRotationVector();
    }
    public void setVelocity(Vec3d vel) {
        if (this.block) {
            return;
        }
        if (this.up > 0) {
            this.player.setVelocity(new Vec3d(vel.x, vel.y + this.up, vel.z));
        } else if (this.down > 0) {
            this.player.setVelocity(new Vec3d(vel.x, vel.y - this.down, vel.z));
        } else {
            this.player.setVelocity(vel);
        }
        this.up = 0;
        this.down = 0;
    }
    public void overrideVelocity(Vec3d vel) {
        this.setVelocity(vel);
        this.block = true;
    }
    public boolean flying() {
        return this.flying;
    }
    public void flying(boolean value) {
        this.flying = value;
    }

    public Vec3d moveUp(double amount) {
        return new Vec3d(0, amount, 0);

    }
    public Vec3d moveDown(double amount) {
        return new Vec3d(0, -amount, 0);
    }
    public Vec3d moveForwards(double amount) {
        return this.getRot().multiply(amount);
    }
    public Vec3d moveBackwards(double amount) {
        return this.getRot().negate().multiply(amount);
    }
    public Vec3d moveLeft(double amount) {
        Vec3d moveAmount = this.getRot().rotateY((float) turnAngle).multiply(amount);
        return new Vec3d(moveAmount.x, 0, moveAmount.z);
    }
    public Vec3d moveRight(double amount) {
        Vec3d moveAmount = this.getRot().rotateY((float) -turnAngle).multiply(amount);
        return new Vec3d(moveAmount.x, 0, moveAmount.z);
    }

    public boolean ready() {
        return this.player != null && this.velocity != null;
    }

    public static PlayerManager getInstance() {
        return instance;
    }
}
