package dev.brightshard.brightcraft.patches;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

public interface PatchedGameOptions {
    KeyBinding forwardKey = MinecraftClient.getInstance().options.forwardKey;
    KeyBinding backKey = MinecraftClient.getInstance().options.backKey;
    KeyBinding leftKey = MinecraftClient.getInstance().options.leftKey;
    KeyBinding rightKey = MinecraftClient.getInstance().options.rightKey;
    KeyBinding jumpKey = MinecraftClient.getInstance().options.jumpKey;
    KeyBinding sneakKey = MinecraftClient.getInstance().options.sneakKey;
    void setGamma(double gamma);
    double getGamma();
}
