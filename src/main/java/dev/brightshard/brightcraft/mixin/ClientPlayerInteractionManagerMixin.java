package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.lib.Event.DataEvent;
import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Event.SimpleEvent;
import dev.brightshard.brightcraft.lib.LockedBuffer;
import dev.brightshard.brightcraft.patches.PatchedInteractionManager;
import dev.brightshard.brightcraft.hacks.Instabreak;

import net.minecraft.client.network.ClientPlayNetworkHandler;
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
public abstract class ClientPlayerInteractionManagerMixin implements PatchedInteractionManager {
    @Shadow
    private float currentBreakingProgress;
    @Shadow
    @Final
    private ClientPlayNetworkHandler networkHandler;

    public void sendAction(PlayerActionC2SPacket.Action action, BlockPos pos, Direction direction) {
        this.networkHandler.sendPacket(new PlayerActionC2SPacket(action, pos, direction));
    }
    public void setCurrentBreakingProgress(int amount) {
        this.currentBreakingProgress = amount;
    }

    @Inject(method = "updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
    at = @At("HEAD"))
    private void updateBlockBreakingProgress(BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        try (LockedBuffer<DataEvent<Instabreak.BlockInfo>>.Lock lock = Events.BlockBreaking.lock()) {
            lock.readBuffer().fire(new Instabreak.BlockInfo(pos, dir));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        try (LockedBuffer<SimpleEvent>.Lock lock = Events.BreakingProgressChanged.lock()) {
            lock.readBuffer().fire();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
