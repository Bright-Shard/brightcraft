package dev.brightshard.brightcraft.managers;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface PlayerManager {
    // Tickable events
    void movePlayer();

    // Movement properties
    Vec3d getRot();
    Vec3d getVel();
    void setVel(Vec3d velocity);
    boolean sneaking();
    void sneaking(boolean isSneaking);
    boolean noclip();
    void noclip(boolean enabled);

    // Move player
    void moveForwards(double amount);
    void moveBackwards(double amount);
    void moveLeft(double amount);
    void moveRight(double amount);
    void moveForwardsFlat(double amount);
    void moveBackwardsFlat(double amount);
    void moveUp(double amount);
    void moveDown(double amount);

    // Flight
    boolean flying();
    void flying(boolean isFlying);
    void setFallDistance(float distance);
    void setAirStrafingSpeed(float speed);

    // Other functions
    boolean playerWouldCollide(BlockPos pos);
    void sendPacket(PlayerMoveC2SPacket packet);
    void setCurrentBreakingProgress(int progress);
    InteractionManager getIM();
    void hiddenChat(String text);
}
