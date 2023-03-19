package dev.brightshard.brightcraft.mixin;

import dev.brightshard.brightcraft.lib.Event.*;
import dev.brightshard.brightcraft.lib.LockedBuffer;
import dev.brightshard.brightcraft.patches.PatchedClient;
import dev.brightshard.brightcraft.patches.PatchedGameOptions;
import dev.brightshard.brightcraft.patches.PatchedInteractionManager;
import dev.brightshard.brightcraft.patches.PatchedPlayer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
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
    @Final @Shadow public GameOptions options;
    @Shadow public abstract void setScreen(@Nullable Screen screen);
    @Shadow public ClientPlayerEntity player;
    @Shadow public ClientPlayerInteractionManager interactionManager;

    public GameOptions getOptions() {
        return this.options;
    }
    public PatchedGameOptions getOptionsPatched() {
        return (PatchedGameOptions) this.options;
    }

    public PatchedInteractionManager getInteractionManager() {
        return (PatchedInteractionManager) this.interactionManager;
    }

    @Inject(method = "isAmbientOcclusionEnabled()Z", at = @At(value = "HEAD"), cancellable = true)
    private static void disableAmbientOcclusion(CallbackInfoReturnable<Boolean> cir) {
        try (LockedBuffer<ReturnableEvent<Boolean>>.Lock lock = Events.DisableAmbientOcclusion.lock()) {
            Boolean disable = lock.readBuffer().fire();
            if (disable != null && disable) {
                cir.cancel();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
