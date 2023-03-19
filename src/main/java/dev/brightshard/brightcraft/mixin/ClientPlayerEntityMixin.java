package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.lib.Event.Events;

import dev.brightshard.brightcraft.lib.Event.ReturnableEvent;
import dev.brightshard.brightcraft.lib.LockedBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.network.ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(method = "getUnderwaterVisibility", at = @At("HEAD"), cancellable = true)
    public void getUnderwaterVisibility(CallbackInfoReturnable<Float> cir) {
        try (LockedBuffer<ReturnableEvent<Boolean>>.Lock lock = Events.MaxUnderwaterVisibility.lock()) {
            Boolean nullify = lock.readBuffer().fire();
            if (nullify != null && nullify) {
                cir.setReturnValue(0f);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
