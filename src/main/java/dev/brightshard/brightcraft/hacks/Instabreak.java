package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.Event.*;
import dev.brightshard.brightcraft.lib.Hack;
import dev.brightshard.brightcraft.lib.Config.ConfigItem;
import static dev.brightshard.brightcraft.BrightCraft.CLIENT;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;

public class Instabreak extends Hack {
    private final ConfigItem<Boolean> bypass = new ConfigItem<>("Instabreak.Bypass", false);

    public static class BlockInfo {
        public BlockPos pos;
        public Direction dir;

        public BlockInfo(BlockPos pos, Direction dir) {
            this.pos = pos;
            this.dir = dir;
        }
    }

    public Instabreak() {
        super(
                HackType.Instabreak,
                "Instant Mine",
                "Mine blocks much faster",
                GLFW.GLFW_KEY_I
        );
        this.listen(Events.BlockBreaking, this::blockBreaking);
        this.listen(Events.BreakingProgressChanged, this::breakingProgressChanged);
    }

    private void breakingProgressChanged() {
        if (!this.bypass.getRawValue()) {
            CLIENT.getInteractionManager().setCurrentBreakingProgress(0);
        }
    }

    private void blockBreaking(BlockInfo info) {
        CLIENT.getInteractionManager().sendAction(
                PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK,
                info.pos,
                info.dir
        );
    }
}
