package dev.brightshard.brightcraft.mixin;

import static dev.brightshard.brightcraft.BrightCraft.*;

import dev.brightshard.brightcraft.lib.Event.EventType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.world.ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method="disconnect", at=@At("HEAD"))
    public void disconnect(CallbackInfo ci) {
        EVENTS.fire(EventType.WorldLeft);
    }
}
