package dev.brightshard.brightcraft.patches;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.WorldRenderer;

public interface PatchedClient {
    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    WorldRenderer worldRenderer = MinecraftClient.getInstance().worldRenderer;
    GameOptions getOptions();
    PatchedGameOptions getOptionsPatched();
    void setScreen(Screen screen);
    PatchedInteractionManager getInteractionManager();
}
