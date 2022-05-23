package dev.brightshard.brightcraft.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.brightshard.brightcraft.Main.LOGGER;
import dev.brightshard.brightcraft.lib.Hack;

@Mixin(net.minecraft.client.render.GameRenderer.class)
public class GameRendererMixin {
    private static Hack fullbright = Hack.getHackById("FullBright");

    public GameRendererMixin() {
        LOGGER.info("GameRendererMixin loaded");
    }

    @Inject(method = "getNightVisionStrength", at = @At(value = "HEAD"), cancellable = true)
    private static void nightVision(LivingEntity entity, float tickDelta, CallbackInfoReturnable<Float> cir) {
        if (fullbright == null) {
            fullbright = Hack.getHackById("FullBright");
        } else if (fullbright.enabled()) {
            cir.setReturnValue(1f);
        }
    }
}
