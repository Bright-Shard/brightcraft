package dev.brightshard.brightcraft.patches;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.WorldRenderer;

public interface PatchedClient {
    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    WorldRenderer worldRenderer = MinecraftClient.getInstance().worldRenderer;
    PatchedGameOptions getOptions();
    PatchedPlayer getPlayer();
    ClientPlayerEntity getPlayerRaw();
    void setScreen(Screen screen);
}
