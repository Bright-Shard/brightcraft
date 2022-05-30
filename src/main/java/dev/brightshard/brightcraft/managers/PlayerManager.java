package dev.brightshard.brightcraft.managers;

import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class PlayerManager {
    private ClientPlayerEntityManager player = null;
    private ClientPlayerEntity playerEntity = null;
    private MinecraftClient client = null;
    private InteractionManager interactionManager = null;
    private Vec3d velocity = new Vec3d(0, 0, 0);
    private boolean flying = false;
    private boolean blockMovement = false;
    private double up = 0;
    private double down = 0;
    private final double turnAngle = Math.toRadians(90);
    private static final PlayerManager instance = new PlayerManager();

    public static PlayerManager getInstance() {
        return instance;
    }

    public void tick(MinecraftClient client) {
        if (client.player != null) {
            if (client != this.client || client.player != this.playerEntity) {
                this.client = client;
                this.player = (ClientPlayerEntityManager) client.player;
                this.playerEntity = this.player.getEntity();
                this.interactionManager = (InteractionManager) client.interactionManager;
            }
            this.velocity = client.player.getVelocity();
        }  else {
            this.client = null;
            this.player = null;
            this.playerEntity = null;
            this.interactionManager = null;
            this.velocity = null;
        }
        this.blockMovement = false;
    }

    // ATTRIBUTES ------------------------------------------------------------------------------------------------------
    public MinecraftClient getClient() {
        return this.client;
    }
    public InteractionManager getInteractionManager() {
        return this.interactionManager;
    }
    public ClientPlayerEntity getPlayerEntity() {
        return this.playerEntity;
    }
    public Vec3d getVelocity() {
        return this.velocity;
    }
    public Vec3d getRot() {
        return this.playerEntity.getRotationVector();
    }
    public void setNoClip(boolean enabled) {
        this.playerEntity.noClip = enabled;
    }
    public float getFallDistance() {
        return this.playerEntity.fallDistance;
    }
    public void setFallDistance(float distance) {
        this.playerEntity.fallDistance = distance;
    }
    public boolean flying() {
        return this.flying;
    }
    public void flying(boolean value) {
        this.flying = value;
    }

    // VELOCITY --------------------------------------------------------------------------------------------------------
    public void setVelocity(Vec3d vel) {
        if (this.blockMovement) {
            return;
        }
        if (this.up > 0) {
            this.playerEntity.setVelocity(new Vec3d(vel.x, vel.y + this.up, vel.z));
        } else if (this.down > 0) {
            this.playerEntity.setVelocity(new Vec3d(vel.x, vel.y - this.down, vel.z));
        } else {
            this.playerEntity.setVelocity(vel);
        }
        this.up = 0;
        this.down = 0;
    }
    public void overrideVelocity(Vec3d vel) {
        this.setVelocity(vel);
        this.blockMovement = true;
    }

    // MOVEMENT --------------------------------------------------------------------------------------------------------
    public Vec3d moveUp(double amount) {
        return new Vec3d(0, this.getRot().getY() + amount, 0);

    }
    public Vec3d moveDown(double amount) {
        return new Vec3d(0, this.getRot().getY() - amount, 0);
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

    // CHECKS ----------------------------------------------------------------------------------------------------------
    public boolean wouldCollide(BlockPos pos) {
        return this.player.playerWouldCollide(pos);
    }

    // UTILITIES -------------------------------------------------------------------------------------------------------
    public void localChat(String message) {
        this.playerEntity.sendSystemMessage(Text.of(message), null);
    }
    public void sendPacket(PlayerMoveC2SPacket packet) {
        this.playerEntity.networkHandler.sendPacket(packet);
    }
}
