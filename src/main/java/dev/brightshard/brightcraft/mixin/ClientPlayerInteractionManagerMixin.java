package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventManager;
import dev.brightshard.brightcraft.managers.InteractionManager;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.network.ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin implements InteractionManager {
    @Shadow
    private float currentBreakingProgress;
    @Shadow
    protected abstract void sendPlayerAction(PlayerActionC2SPacket.Action action, BlockPos pos, Direction direction);

    @Override
    public void sendAction(PlayerActionC2SPacket.Action action, BlockPos pos, Direction direction) {
        sendPlayerAction(action, pos, direction);
    }
    @Override
    public void setCurrentBreakingProgress(int amount) {
        this.currentBreakingProgress = amount;
    }

    @Inject(method = "updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
    at = @At("HEAD"))
    private void updateBlockBreakingProgress(BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        EventManager.fireEvent("BlockBreaking", new EventData<>(new Object[]{pos, dir}));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        EventManager.fireEvent("BreakingProgressChanged");
    }
}
