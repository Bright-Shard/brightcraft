package dev.brightshard.brightcraft.patches;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public interface PatchedGameOptions {
    void setGamma(double gamma);
    double getGamma();
}
