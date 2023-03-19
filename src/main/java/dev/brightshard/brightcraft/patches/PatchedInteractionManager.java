package dev.brightshard.brightcraft.patches;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public interface PatchedInteractionManager {
    void sendAction(PlayerActionC2SPacket.Action action, BlockPos pos, Direction direction);
    void setCurrentBreakingProgress(int amount);
}
