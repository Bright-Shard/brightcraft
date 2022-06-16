package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.Main;
import dev.brightshard.brightcraft.events.EventData;
import dev.brightshard.brightcraft.events.EventManager;
import dev.brightshard.brightcraft.managers.ClientManager;
import dev.brightshard.brightcraft.managers.GameOptionsManager;
import dev.brightshard.brightcraft.managers.PlayerManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements ClientManager {
    private ClientPlayerEntity player;
    @Final
    @Shadow
    public GameOptions options;
    @Shadow
    public abstract void setScreen(@Nullable Screen screen);

    public GameOptionsManager getOptions() {
        return (GameOptionsManager) this.options;
    }

    public PlayerManager getPlayer() {
        if (Main.MC.player != null) {
            this.player = Main.MC.player;
        }

        return (PlayerManager) this.player;
    }
    public ClientPlayerEntity getPlayerRaw() {
        if (Main.MC.player != null) {
            this.player = Main.MC.player;
        }

        return this.player;
    }

    @Inject(method = "isAmbientOcclusionEnabled()Z", at = @At(value = "HEAD"), cancellable = true)
    private static void disableAmbientOcclusion(CallbackInfoReturnable<Boolean> cir) {
        EventManager.fireEvent("AmbientOcclusion", new EventData<>(cir));
    }
}
