package dev.brightshard.brightcraft.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;

import dev.brightshard.brightcraft.lib.Hack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    private static Hack xray = Hack.getHackById("XRay");

    @Inject(method = "isAmbientOcclusionEnabled()Z", at = @At(value = "HEAD"), cancellable = true)
    private static void disableAmbientOcclusion(CallbackInfoReturnable<Boolean> cir) {
        if (xray == null) {
            xray = Hack.getHackById("XRay");
        } else if(xray.enabled()) {
            cir.setReturnValue(false);
        }
    }
}
