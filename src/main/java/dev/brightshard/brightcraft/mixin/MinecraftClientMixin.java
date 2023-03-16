package dev.brightshard.brightcraft.mixin;

import static dev.brightshard.brightcraft.BrightCraft.EVENTS;
import dev.brightshard.brightcraft.lib.Event.*;
import dev.brightshard.brightcraft.patches.PatchedClient;
import dev.brightshard.brightcraft.patches.PatchedGameOptions;
import dev.brightshard.brightcraft.patches.PatchedPlayer;

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
public abstract class MinecraftClientMixin implements PatchedClient {
    @Final
    @Shadow
    public GameOptions options;
    @Shadow
    public abstract void setScreen(@Nullable Screen screen);
    @Shadow
    public ClientPlayerEntity player;

    public PatchedGameOptions getOptions() {
        return (PatchedGameOptions) this.options;
    }

    public PatchedPlayer getPlayer() {
        return (PatchedPlayer) this.player;
    }
    public ClientPlayerEntity getPlayerRaw() {
        return this.player;
    }

    @Inject(method = "isAmbientOcclusionEnabled()Z", at = @At(value = "HEAD"), cancellable = true)
    private static void disableAmbientOcclusion(CallbackInfoReturnable<Boolean> cir) {
        Boolean disable = EVENTS.fireReturnable(EventType.DisableAmbientOcclusion);
        if (disable != null && disable) {
            cir.cancel();
        }
    }
}
