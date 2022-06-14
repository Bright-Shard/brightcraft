package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventManager;
import dev.brightshard.brightcraft.managers.ClientManager;
import dev.brightshard.brightcraft.managers.GameOptionsManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements ClientManager {
    @Final
    @Shadow
    public GameOptions options;
    @Shadow
    public abstract void setScreen(@Nullable Screen screen);

    public GameOptionsManager getOptions() {
        return (GameOptionsManager) this.options;
    }

    @Inject(method = "isAmbientOcclusionEnabled()Z", at = @At(value = "HEAD"), cancellable = true)
    private static void disableAmbientOcclusion(CallbackInfoReturnable<Boolean> cir) {
        EventManager.fireEvent("AmbientOcclusion", new EventData<>(cir));
    }
}
