package dev.brightshard.brightcraft.mixin;

import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.brightshard.brightcraft.Main;

@Mixin(net.minecraft.client.network.ClientPlayNetworkHandler.class)
public class ClientPlayerNetworkHandlerMixin {
    @Inject(method = "onGameJoin", at = @At("TAIL"))
    private void onGameJoin(GameJoinS2CPacket par1, CallbackInfo ci) {
        Main.getInstance().worldJoined = true;
    }
}
