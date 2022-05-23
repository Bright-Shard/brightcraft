package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.lib.Config;
import dev.brightshard.brightcraft.lib.Hack;
import static dev.brightshard.brightcraft.Main.LOGGER;

import dev.brightshard.brightcraft.lib.PlayerManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.network.ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Final
    @Shadow
    private MinecraftClient client;
    @Shadow
    private float currentBreakingProgress;

    @Shadow
    protected abstract void sendPlayerAction(PlayerActionC2SPacket.Action action, BlockPos pos, Direction direction);
    @Shadow
    public abstract boolean breakBlock(BlockPos pos);

    private Hack instabreak = Hack.getHackById("Instabreak");
    private PlayerManager playerManager = PlayerManager.getInstance();

    @Inject(method = "updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
    at = @At("HEAD"))
    private void updateBlockBreakingProgress(BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (client.interactionManager == null || instabreak == null) {
            instabreak = Hack.getHackById("Instabreak");
            playerManager = PlayerManager.getInstance();
        } else if (instabreak.enabled()) {
            this.sendPlayerAction(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, dir);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (instabreak == null || playerManager == null ||!playerManager.ready()) {
            instabreak = Hack.getHackById("Instabreak");
            playerManager = PlayerManager.getInstance();
        } else if (instabreak.enabled() && Config.getInstance().getConfig("Instabreak.Bypass").equals("false")) {
            this.currentBreakingProgress = 0;
        }
    }
}
