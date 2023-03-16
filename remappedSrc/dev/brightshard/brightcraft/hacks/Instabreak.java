package dev.brightshard.brightcraft.hacks;

import dev.brightshard.brightcraft.lib.EventData;
import dev.brightshard.brightcraft.lib.EventHandler;
import dev.brightshard.brightcraft.lib.Hack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;

public class Instabreak extends Hack {
    public Instabreak() {
        super("Instabreak", "Instant Mine", "Mine blocks much faster", GLFW.GLFW_KEY_I);
        Instabreak instance = this;
        this.handlers.add(new EventHandler(instance.id, "BlockBreaking") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                Object[] blockInfo = data.getData();
                CLIENT.getPlayer().getIM().sendAction(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, (BlockPos) blockInfo[0], (Direction) blockInfo[1]);
            }
        });
        this.handlers.add(new EventHandler(instance.id, "BreakingProgressChanged") {
            @Override
            public <DataType, CIRType> void fire(EventData<DataType, CIRType> data) {
                if (!Boolean.parseBoolean(CONFIG.getConfig("InstantMine.Bypass"))) {
                    CLIENT.getPlayer().setCurrentBreakingProgress(0);
                }
            }
        });
    }
}
