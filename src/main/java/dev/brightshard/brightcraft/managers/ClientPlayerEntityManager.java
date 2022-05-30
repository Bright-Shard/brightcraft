package dev.brightshard.brightcraft.managers;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;

public interface ClientPlayerEntityManager {
    // Other functions
    boolean playerWouldCollide(BlockPos pos);
    ClientPlayerEntity getEntity();
}
