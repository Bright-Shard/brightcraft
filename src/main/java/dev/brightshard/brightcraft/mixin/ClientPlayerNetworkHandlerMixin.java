package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.lib.Event.Events;
import dev.brightshard.brightcraft.lib.Event.SimpleEvent;
import dev.brightshard.brightcraft.lib.LockedBuffer;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.network.ClientPlayNetworkHandler.class)
public class ClientPlayerNetworkHandlerMixin {
    @Inject(method = "onGameJoin", at = @At("TAIL"))
    private void onGameJoin(GameJoinS2CPacket par1, CallbackInfo ci) {
        try (LockedBuffer<SimpleEvent>.Lock lock = Events.WorldJoined.lock()) {
            lock.readBuffer().fire();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
