package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.world.ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method="disconnect", at=@At("HEAD"))
    public void disconnect(CallbackInfo ci) {
        Main.getInstance().onWorldLeave();
    }
}
