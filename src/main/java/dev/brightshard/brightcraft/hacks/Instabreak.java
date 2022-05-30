package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;

public class Instabreak extends Hack {
    public Instabreak() {
        super("Instabreak", "Instant Mine", "Mine blocks much faster", GLFW.GLFW_KEY_I);
    }

    @Override
    public void onEnable() {
        eventManager.bindEvent("BlockBreaking", this.id, (cir, data) -> {
            Object[] blockData = (Object[]) data;
            player.getInteractionManager().sendAction(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, (BlockPos) blockData[0], (Direction) blockData[1]);
        });
        eventManager.bindEvent("BreakingProgressChanged", this.id, (cir, data) -> player.getInteractionManager().setCurrentBreakingProgress(0));
    }
    @Override
    public void onDisable() {
        eventManager.releaseEvent("BlockBreaking", this.id);
    }

    @Override
    public void tick() {

    }
}
